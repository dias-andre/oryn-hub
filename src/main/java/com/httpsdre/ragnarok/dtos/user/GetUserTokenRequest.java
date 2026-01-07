package com.httpsdre.ragnarok.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record GetUserTokenRequest(
        @NotBlank String token
) {
}
