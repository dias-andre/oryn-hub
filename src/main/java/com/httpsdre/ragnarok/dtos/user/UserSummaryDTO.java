package com.httpsdre.ragnarok.dtos.user;

import java.time.OffsetDateTime;

public record UserSummaryDTO(
        String id,
        String discordId,
        String displayName,
        String username,
        String email,
        String avatar,
        OffsetDateTime createdAt,
        OffsetDateTime lastLogin

) {
}
