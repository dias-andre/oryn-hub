package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.models.Member;

public class MemberMapper {
  public static MemberSummaryDTO
    toSummary(Member model) {
    if(model == null) return null;

    return MemberSummaryDTO.builder()
            .id(model.getUser().getId().toString())
            .discordId(model.getUser().getDiscordId())
            .displayName(model.getUser().getDisplayName())
            .username(model.getUser().getUsername())
            .role(model.getRole())
            .joinedAt(model.getJoinedAt())
            .build();
  }
}
