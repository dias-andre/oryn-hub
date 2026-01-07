package com.httpsdre.ragnarok.dtos;

import com.httpsdre.ragnarok.types.UserRole;

import java.time.LocalDateTime;

public record UserDetailsDTO(
        String id,
        String discordId,
        String displayName,
        String username,
        String email,
        String avatar,
        UserRole role,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime lastLogin

) {
}
