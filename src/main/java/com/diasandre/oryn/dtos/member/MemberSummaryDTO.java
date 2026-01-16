package com.diasandre.oryn.dtos.member;

import com.diasandre.oryn.types.SquadRole;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record MemberSummaryDTO(
        String id,
        String discordId,
        String displayName,
        String username,
        SquadRole role,
        OffsetDateTime joinedAt
) {
}
