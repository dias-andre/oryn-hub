package com.httpsdre.ragnarok.mappers;

import com.httpsdre.ragnarok.dtos.invite.InviteSummaryDTO;
import com.httpsdre.ragnarok.dtos.member.InviteAuthorDTO;
import com.httpsdre.ragnarok.models.Invite;

public class InviteMapper {
  public static InviteSummaryDTO toSummary(Invite i, InviteAuthorDTO author) {
    return InviteSummaryDTO.builder()
            .id(i.getId())
            .code(i.getCode())
            .usageCount(i.getUsageCount())
            .usageLimit(i.getUsageLimit())
            .isPaused(i.isPaused())
            .expiresAt(i.getExpiresAt())
            .createdAt(i.getCreatedAt())
            .author(author)
            .build();

  }
}
