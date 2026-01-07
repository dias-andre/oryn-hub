package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.UserDetailsDTO;
import com.httpsdre.ragnarok.models.User;

import java.util.UUID;

public class UserMapper {
  public static UserDetailsDTO fromModel(User model) {
    return new UserDetailsDTO(model.getId().toString(), model.getDiscordId(),
            model.getDisplayName(), model.getUsername(),
            model.getEmail(), model.getAvatar(),
            model.getRole(), model.isActive(),
            model.getCreatedAt(), model.getLastLogin());
  }

  public static User fromUserDetailsDTO(UserDetailsDTO dto) {
    return new User(UUID.fromString(dto.id()), dto.discordId(),
            dto.displayName(), dto.username(),
            dto.email(), dto.avatar(),
            dto.role(), dto.isActive(),
            dto.createdAt(), dto.lastLogin());
  }
}
