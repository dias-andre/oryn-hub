package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.GetCurrentUserRequest;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.dtos.user.LoginResponse;
import com.httpsdre.ragnarok.dtos.user.UserSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.mappers.SquadMapper;
import com.httpsdre.ragnarok.mappers.UserMapper;
import com.httpsdre.ragnarok.models.Squad;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.providers.DiscordProvider;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final SquadRepository squadRepository;
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
      newUser.setCreatedAt(OffsetDateTime.now());
      return newUser;
    });

    user.setAvatar(discordUser.avatar());
    user.setEmail(discordUser.email());
    user.setDisplayName(discordUser.global_name());
    user.setUsername(discordUser.username());
    user.setLastLogin(OffsetDateTime.now());

    user = this.userRepository.save(user);

    String jwt = this.tokenService.generateUserToken(user);
    return new LoginResponse(jwt, UserMapper.toSummary(user));
  }

  public UserSummaryDTO getUser(UUID userId) {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found!"));
    return UserMapper.toSummary(user);
  }

  public List<SquadSummaryDTO> getUserSquads(UUID userId) {
    List<Squad> squads = this.squadRepository.getUserSquads(userId);
    return squads.stream()
            .map(SquadMapper::toSummary)
            .toList();
  }

  public void deleteUser(UUID userId) {
    if(this.userRepository.existsById(userId)) {
      this.userRepository.deleteById(userId);
    }
  }
}
