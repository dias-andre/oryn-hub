package com.diasandre.oryn.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailAndPasswordRequest(
        @NotBlank @Email String email,
        @NotBlank String password
) {
}
