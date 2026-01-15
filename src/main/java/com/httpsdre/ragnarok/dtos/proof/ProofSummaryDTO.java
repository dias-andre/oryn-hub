package com.httpsdre.ragnarok.dtos.proof;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record ProofSummaryDTO(
        UUID id,
        String fileKey,
        String originalName,
        String contentType,
        String description,
        String url,
        OffsetDateTime createdAt
) {
}
