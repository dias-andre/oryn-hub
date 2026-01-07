package com.httpsdre.ragnarok.dtos.user;

import java.time.LocalDateTime;

public record UserSummaryDTO(
        String id,
        String discordId,
        String displayName,
        String username,
        String email,
        String avatar,
        boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime lastLogin

) {
}
