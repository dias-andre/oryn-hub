package com.diasandre.oryn.dtos.giveaway;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateGiveawayRequest(
        @NotBlank @Max(255) String title,
        String prizeDescription,
        @NotBlank OffsetDateTime startsAt,
        @NotBlank OffsetDateTime endsAt,
        boolean isDone,
        UUID squadId
) {

}
