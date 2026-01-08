package com.httpsdre.ragnarok.dtos.member;

import lombok.Builder;

import java.util.UUID;

@Builder
public record InviteAuthorDTO(
        UUID id,
        String displayName,
        String username,
        String avatar
) {
}
