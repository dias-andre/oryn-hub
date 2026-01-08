package com.httpsdre.ragnarok.dtos.invite;

import com.httpsdre.ragnarok.dtos.member.InviteAuthorDTO;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record InviteSummaryDTO(
        UUID id,
        String code,
        Integer usageCount,
        Integer usageLimit,
        boolean isPaused,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        InviteAuthorDTO author
) {
}
