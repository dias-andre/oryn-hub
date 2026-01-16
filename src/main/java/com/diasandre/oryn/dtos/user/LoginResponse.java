package com.diasandre.oryn.dtos.user;

public record LoginResponse(
        String jwt,
        UserSummaryDTO user
) {
}
