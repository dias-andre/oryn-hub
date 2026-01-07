package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.models.Member;

public class MemberMapper {
  public static MemberSummaryDTO
    toSummary(Member model) {
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
