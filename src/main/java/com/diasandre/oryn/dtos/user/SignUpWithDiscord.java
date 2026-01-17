package com.diasandre.oryn.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record SignUpWithDiscord(
        @NotBlank String discordToken,
        @NotBlank String displayName,
        String avatar,
        @NotBlank String password
) {
}
