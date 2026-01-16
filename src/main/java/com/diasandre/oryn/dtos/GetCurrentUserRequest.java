package com.diasandre.oryn.dtos;

import jakarta.validation.constraints.NotBlank;

public record GetCurrentUserRequest(
        @NotBlank String id,
        @NotBlank String username,
        @NotBlank String global_name,
        String avatar,
        String email
) {
}
