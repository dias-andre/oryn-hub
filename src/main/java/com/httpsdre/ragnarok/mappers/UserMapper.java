package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.user.UserSummaryDTO;
import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.User;

import java.util.List;
import java.util.UUID;

public class UserMapper {
  public static UserSummaryDTO toSummary(User model) {
    return new UserSummaryDTO(model.getId().toString(), model.getDiscordId(),
            model.getDisplayName(), model.getUsername(),
            model.getEmail(), model.getAvatar(), model.isActive(),
            model.getCreatedAt(), model.getLastLogin());
  }

  public static User fromUserDetailsDTO(UserSummaryDTO dto, List<Member> members) {
    return new User(UUID.fromString(dto.id()), dto.discordId(),
            dto.displayName(), dto.username(),
            dto.email(), dto.avatar(), dto.isActive(),
            dto.createdAt(), dto.lastLogin(), null);
  }
}
