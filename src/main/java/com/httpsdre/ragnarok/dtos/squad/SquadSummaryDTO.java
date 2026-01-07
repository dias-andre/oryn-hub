package com.httpsdre.ragnarok.dtos.squad;

import lombok.Builder;

@Builder
public record SquadSummaryDTO(
        String id,
        String name
) {
}
