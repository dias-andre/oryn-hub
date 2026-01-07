package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.models.Squad;

public class SquadMapper {
  public SquadSummaryDTO toSummary(Squad model) {
    return SquadSummaryDTO.builder()
            .id(model.getId().toString())
            .name(model.getName())
            .build();
  }
}
