package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.user.UserSummaryDTO;
import com.diasandre.oryn.models.Member;
import com.diasandre.oryn.models.User;

import java.util.List;
import java.util.UUID;

public class UserMapper {
  public static UserSummaryDTO toSummary(User model) {
    return new UserSummaryDTO(model.getId().toString(), model.getDiscordId(),
            model.getDisplayName(), model.getUsername(),
            model.getEmail(), model.getAvatar(),
            model.getCreatedAt(), model.getLastLogin());
  }

  public static User fromUserDetailsDTO(UserSummaryDTO dto, List<Member> members) {
    return new User(UUID.fromString(dto.id()), dto.discordId(),
            dto.displayName(), dto.username(),
            dto.email(), dto.avatar(),
            dto.createdAt(), dto.lastLogin(), null);
  }
}
