package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.invite.InviteSummaryDTO;
import com.diasandre.oryn.dtos.member.InviteAuthorDTO;
import com.diasandre.oryn.models.Invite;

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
