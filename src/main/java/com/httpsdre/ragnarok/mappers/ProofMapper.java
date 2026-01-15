package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.proof.ProofSummaryDTO;
import com.httpsdre.ragnarok.models.Proof;

public class ProofMapper {
  public static ProofSummaryDTO toSummary(Proof model) {
    return ProofSummaryDTO.builder()
            .id(model.getId())
            .fileKey(model.getFileKey())
            .originalName(model.getOriginalName())
            .contentType(model.getContentType())
            .url(model.getUrl())
            .description(model.getDescription())
            .createdAt(model.getCreatedAt())
            .build();
  }
}
