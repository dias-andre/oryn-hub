package com.httpsdre.ragnarok.dtos;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record ErrorDTO(
        String message,
        OffsetDateTime timestamp
) {
}
