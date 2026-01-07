package com.httpsdre.ragnarok.dtos;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorDTO(
        String message,
        LocalDateTime timestamp
) {
}
