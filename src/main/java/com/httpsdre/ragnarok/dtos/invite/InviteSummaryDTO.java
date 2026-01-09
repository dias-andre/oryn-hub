package com.httpsdre.ragnarok.dtos.invite;

import com.httpsdre.ragnarok.dtos.member.InviteAuthorDTO;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record InviteSummaryDTO(
        UUID id,
        String code,
        Integer usageCount,
        Integer usageLimit,
        boolean isPaused,
        OffsetDateTime createdAt,
        OffsetDateTime expiresAt,
        InviteAuthorDTO author
) {
}
