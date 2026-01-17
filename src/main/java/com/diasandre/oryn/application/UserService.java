package com.diasandre.oryn.application;

import com.diasandre.oryn.dtos.GetCurrentUserRequest;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.dtos.user.LoginResponse;
import com.diasandre.oryn.dtos.user.SignUpWithDiscord;
import com.diasandre.oryn.dtos.user.UserSummaryDTO;
import com.diasandre.oryn.exceptions.BusinessException;
import com.diasandre.oryn.exceptions.NotFoundException;
import com.diasandre.oryn.exceptions.UnauthorizedException;
import com.diasandre.oryn.mappers.SquadMapper;
import com.diasandre.oryn.mappers.UserMapper;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.providers.DiscordProvider;
import com.diasandre.oryn.repositories.SquadRepository;
import com.diasandre.oryn.repositories.UserRepository;
import com.diasandre.oryn.types.ErrorCode;
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
  public LoginResponse authUser(String token) {
    GetCurrentUserRequest discordUser;
    try {
      discordUser = this.discord.getCurrentUser(token);
    } catch (Exception _) {
      System.out.println("Usuário do discord não encontrado.");
      throw new UnauthorizedException("Discord user not found!");
    }

    User user = this.userRepository.findByDiscordId(discordUser.id())
            .orElseGet(() -> {
              var userWithEmail = this.userRepository.findByEmail(discordUser.email());
              if(userWithEmail.isPresent()) {
                throw new BusinessException(ErrorCode.EMAIL_EXISTS);
              }
              User newUser = new User();
              newUser.setDiscordId(discordUser.id());
              newUser.setEmail(discordUser.email());
              newUser.setDisplayName(discordUser.global_name());
              newUser.setAvatar(discordUser.avatar());
              return newUser;
            });

//    user.setAvatar(discordUser.avatar());
//    user.setEmail(discordUser.email());
//    user.setDisplayName(discordUser.global_name());
    user.setUsername(discordUser.username());
    user.setLastLogin(OffsetDateTime.now());

    user = this.userRepository.save(user);

    String jwt = this.tokenService.generateUserToken(user);
    return new LoginResponse(jwt, UserMapper.toSummary(user));
  }

  @Transactional
  public LoginResponse createUser(SignUpWithDiscord data) {
    GetCurrentUserRequest discordUser;
    try {
      discordUser = this.discord.getCurrentUser(data.discordToken());
    } catch (Exception _) {
      throw new UnauthorizedException("Discord user not found!");
    }

    if(discordUser.email() == null) {
      throw new BusinessException(ErrorCode.INVALID_EMAIL);
    }

    var userWithEmail = this.userRepository.findByEmail(discordUser.email());
    if(userWithEmail.isPresent()) { // here, user already exists
      String jwt = this.tokenService.generateUserToken(userWithEmail.get());
      return new LoginResponse(jwt, UserMapper.toSummary(userWithEmail.get()));
    }

    User newUser = new User();
    newUser.setDiscordId(data.displayName());
    newUser.setAvatar(data.avatar());

    newUser.setDiscordId(discordUser.id());
    newUser.setEmail(discordUser.email());
    newUser.setLastLogin(OffsetDateTime.now());
    newUser.setUsername(discordUser.username());

    newUser = this.userRepository.save(newUser);

    String jwt = this.tokenService.generateUserToken(newUser);
    return new LoginResponse(jwt, UserMapper.toSummary(newUser));
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
