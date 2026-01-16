package com.diasandre.oryn.mappers;

import com.diasandre.oryn.dtos.giveaway.GiveawaySummaryDTO;
import com.diasandre.oryn.models.Giveaway;
import com.diasandre.oryn.models.User;

public class GiveawayMapper {
  public static GiveawaySummaryDTO toSummary(Giveaway model, User author) {
    return GiveawaySummaryDTO.builder()
            .id(model.getId())
            .title(model.getTitle())
            .prizeDescription(model.getPrizeDescription())
            .startsAt(model.getStartsAt())
            .endsAt(model.getEndsAt())
            .createdAt(model.getCreatedAt())
            .author(new GiveawaySummaryDTO.Author(
                    author.getId(),
                    author.getDisplayName(),
                    author.getUsername(),
                    author.getAvatar()
            )).build();
  }

  public static GiveawaySummaryDTO toSummary(Giveaway model) {
    var author = model.getAuthor();
    return GiveawaySummaryDTO.builder()
            .id(model.getId())
            .title(model.getTitle())
            .prizeDescription(model.getPrizeDescription())
            .startsAt(model.getStartsAt())
            .endsAt(model.getEndsAt())
            .createdAt(model.getCreatedAt())
            .author(new GiveawaySummaryDTO.Author(
                    author.getId(),
                    author.getDisplayName(),
                    author.getUsername(),
                    author.getAvatar()
            )).build();
  }
}
