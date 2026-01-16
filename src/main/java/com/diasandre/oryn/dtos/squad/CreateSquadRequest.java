package com.diasandre.oryn.dtos.squad;

import jakarta.validation.constraints.NotBlank;

public record CreateSquadRequest(
        @NotBlank String name,
        String icon
) {
}
