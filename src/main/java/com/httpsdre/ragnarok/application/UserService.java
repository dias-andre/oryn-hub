package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.GetCurrentUserRequest;
import com.httpsdre.ragnarok.dtos.LoginResponse;
import com.httpsdre.ragnarok.dtos.UserDetailsDTO;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.mappers.UserMapper;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.providers.DiscordProvider;
import com.httpsdre.ragnarok.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final DiscordProvider discord;
  private final TokenService tokenService;

  @Transactional
  public LoginResponse authUserByToken(String token) {
    GetCurrentUserRequest discordUser;
    try {
      discordUser = this.discord.getCurrentUser(token);
    } catch (Exception _) {
      throw new UnauthorizedException("Discord user not found!");
    }
    User user = this.userRepository.findByDiscordId(discordUser.id())
            .orElseThrow(() -> new UnauthorizedException("User not found!"));

    user.setActive(true);
    user.setAvatar(discordUser.avatar());
    user.setEmail(discordUser.email());
    user.setDisplayName(discordUser.global_name());
    user.setUsername(discordUser.username());
    String jwt = this.tokenService.generateUserToken(user);
    return new LoginResponse(jwt, UserMapper.fromModel(user));
  }

}
