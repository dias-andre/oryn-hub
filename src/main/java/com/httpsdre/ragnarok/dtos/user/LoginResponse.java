package com.httpsdre.ragnarok.dtos.user;

public record LoginResponse(
        String jwt,
        UserDetailsDTO user
) {
}
