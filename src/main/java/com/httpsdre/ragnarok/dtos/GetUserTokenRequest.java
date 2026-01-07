package com.httpsdre.ragnarok.dtos;

import jakarta.validation.constraints.NotBlank;

public record GetUserTokenRequest(
        @NotBlank String token
) {
}
