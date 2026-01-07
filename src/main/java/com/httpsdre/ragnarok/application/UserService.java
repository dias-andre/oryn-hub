package com.httpsdre.ragnarok.application;

import com.fasterxml.uuid.Generators;
import com.httpsdre.ragnarok.dtos.GetCurrentUserRequest;
import com.httpsdre.ragnarok.dtos.user.LoginResponse;
import com.httpsdre.ragnarok.dtos.user.UserSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.mappers.UserMapper;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.providers.DiscordProvider;
import com.httpsdre.ragnarok.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final DiscordProvider discord;
  private final TokenService tokenService;

  @Transactional
  public LoginResponse authAndCreateUser(String token) {
    GetCurrentUserRequest discordUser;
    try {
      discordUser = this.discord.getCurrentUser(token);
    } catch (Exception _) {
      System.out.println("Usuário do discord não encontrado.");
      throw new UnauthorizedException("Discord user not found!");
    }

    User user = this.userRepository.findByDiscordId(discordUser.id()).orElseGet(() -> {
      User newUser = new User();
      newUser.setDiscordId(discordUser.id());
      newUser.setCreatedAt(LocalDateTime.now());
      newUser.setActive(true);
      return newUser;
    });

    user.setActive(true);
    user.setAvatar(discordUser.avatar());
    user.setEmail(discordUser.email());
    user.setDisplayName(discordUser.global_name());
    user.setUsername(discordUser.username());
    user.setLastLogin(LocalDateTime.now());

    user = this.userRepository.save(user);

    String jwt = this.tokenService.generateUserToken(user);
    return new LoginResponse(jwt, UserMapper.toSummary(user));
  }

  public UserSummaryDTO getUserById(UUID id) {
    User user = this.userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
    return UserMapper.toSummary(user);
  }

  public UserSummaryDTO toSummary(User user) {
    return UserMapper.toSummary(user);
  }
}
