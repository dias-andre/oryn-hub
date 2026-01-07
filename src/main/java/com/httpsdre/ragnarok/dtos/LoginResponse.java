package com.httpsdre.ragnarok.dtos;

public record LoginResponse(
        String jwt,
        UserDetailsDTO user
) {
}
