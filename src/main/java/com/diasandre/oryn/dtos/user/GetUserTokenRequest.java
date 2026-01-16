package com.diasandre.oryn.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record GetUserTokenRequest(
        @NotBlank String token
) {
}
