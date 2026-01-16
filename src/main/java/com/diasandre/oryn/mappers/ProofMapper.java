package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.proof.ProofSummaryDTO;
import com.diasandre.oryn.models.Proof;

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
