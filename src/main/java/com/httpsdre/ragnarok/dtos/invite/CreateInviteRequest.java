package com.httpsdre.ragnarok.dtos.invite;

import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public record CreateInviteRequest(
        @Min(0) Integer usageLimit,
        LocalDateTime expiresAt
) {
}
