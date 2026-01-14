package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.giveaway.CreateGiveawayRequest;
import com.httpsdre.ragnarok.dtos.giveaway.GiveawaySummaryDTO;
import com.httpsdre.ragnarok.exceptions.BusinessException;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.mappers.GiveawayMapper;
import com.httpsdre.ragnarok.models.Giveaway;
import com.httpsdre.ragnarok.models.Squad;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.repositories.GiveawayRepository;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
import com.httpsdre.ragnarok.types.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GiveawayService {
  private final GiveawayRepository giveawayRepository;
  private final SquadRepository squadRepository;
  private final UserRepository userRepository;

  @Transactional
  public GiveawaySummaryDTO createGiveaway(UUID authorId, UUID squadId, CreateGiveawayRequest request) {
    User author = this.userRepository.findById(authorId)
            .orElseThrow(() -> new NotFoundException("Giveaway author not found!"));
    Squad squadProxy = this.squadRepository.getReferenceById(squadId);

    if(!request.isDone() && request.endsAt().isBefore(OffsetDateTime.now())) {
      throw new BusinessException(ErrorCode.INVALID_END_DATE);
    }

    Giveaway newGiveaway = new Giveaway();
    newGiveaway.setTitle(request.title());
    newGiveaway.setPrizeDescription(request.prizeDescription());
    newGiveaway.setStartsAt(request.startsAt());
    newGiveaway.setEndsAt(request.endsAt());
    newGiveaway.setAuthor(author);
    newGiveaway.setSquad(squadProxy);
    newGiveaway = this.giveawayRepository.save(newGiveaway);
    return GiveawayMapper.toSummary(newGiveaway, author);
  }

  public List<GiveawaySummaryDTO> getSquadGiveaways(UUID squadId, UUID lastId, int pageSize) {
    int safeSize = (pageSize > 20 || pageSize <= 0) ? 20 : pageSize;
    Pageable limit = PageRequest.of(0, safeSize);
    var giveaways = this.giveawayRepository.findBySquadId_Paged(squadId, lastId, limit);
    return giveaways.stream()
            .map(GiveawayMapper::toSummary)
            .toList();
  }

  public boolean deleteGiveawayById(UUID giveawayId) {
    if(this.giveawayRepository.existsById(giveawayId)) {
      this.giveawayRepository.deleteById(giveawayId);
      return true;
    }
    return false;
  }
}
