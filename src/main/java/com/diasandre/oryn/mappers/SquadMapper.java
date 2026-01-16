package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.models.Squad;

public class SquadMapper {
  public static SquadSummaryDTO toSummary(Squad model) {
    return SquadSummaryDTO.builder()
            .id(model.getId().toString())
            .name(model.getName())
            .icon(model.getIcon())
            .build();
  }
}
