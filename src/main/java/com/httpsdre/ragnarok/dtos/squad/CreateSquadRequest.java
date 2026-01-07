package com.httpsdre.ragnarok.dtos.squad;

import jakarta.validation.constraints.NotBlank;

public record CreateSquadRequest(
        @NotBlank String name
) {
}
