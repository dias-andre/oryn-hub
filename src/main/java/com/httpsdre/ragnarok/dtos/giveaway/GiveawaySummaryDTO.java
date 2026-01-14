package com.httpsdre.ragnarok.dtos.giveaway;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record GiveawaySummaryDTO(
        String title,
        String prizeDescription,
        OffsetDateTime startsAt,
        OffsetDateTime endsAt,
        OffsetDateTime createdAt,
        Author author
) {
  public record Author(
          UUID id,
          String displayName,
          String username,
          String avatar
  ) {

  }
}
