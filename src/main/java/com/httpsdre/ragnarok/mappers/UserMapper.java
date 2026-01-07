package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.user.UserDetailsDTO;
import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.User;

import java.util.List;
import java.util.UUID;

public class UserMapper {
  public static UserDetailsDTO fromModel(User model) {
    return new UserDetailsDTO(model.getId().toString(), model.getDiscordId(),
            model.getDisplayName(), model.getUsername(),
            model.getEmail(), model.getAvatar(), model.isActive(),
            model.getCreatedAt(), model.getLastLogin());
  }

  public static User fromUserDetailsDTO(UserDetailsDTO dto, List<Member> members) {
    return new User(UUID.fromString(dto.id()), dto.discordId(),
            dto.displayName(), dto.username(),
            dto.email(), dto.avatar(), dto.isActive(),
            dto.createdAt(), dto.lastLogin(), null);
  }
}
