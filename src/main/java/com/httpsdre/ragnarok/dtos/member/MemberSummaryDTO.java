package com.httpsdre.ragnarok.dtos.member;

import com.httpsdre.ragnarok.types.SquadRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberSummaryDTO(
        String id,
        String discordId,
        String displayName,
        String username,
        SquadRole role,
        LocalDateTime joinedAt
) {
}
