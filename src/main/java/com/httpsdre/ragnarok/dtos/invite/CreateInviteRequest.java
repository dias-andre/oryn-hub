package com.httpsdre.ragnarok.dtos.invite;

import jakarta.validation.constraints.Min;

import java.time.OffsetDateTime;

public record CreateInviteRequest(
        @Min(0) Integer usageLimit,
        OffsetDateTime expiresAt
) {
}
