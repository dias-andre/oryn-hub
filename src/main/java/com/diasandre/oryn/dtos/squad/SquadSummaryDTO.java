package com.diasandre.oryn.dtos.squad;

import lombok.Builder;

@Builder
public record SquadSummaryDTO(
        String id,
        String name,
        String icon
) {
}
