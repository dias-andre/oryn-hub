package com.diasandre.oryn.application;

import com.diasandre.oryn.dtos.giveaway.CreateGiveawayRequest;
import com.diasandre.oryn.dtos.giveaway.GiveawaySummaryDTO;
import com.diasandre.oryn.exceptions.BusinessException;
import com.diasandre.oryn.exceptions.NotFoundException;
import com.diasandre.oryn.mappers.GiveawayMapper;
import com.diasandre.oryn.models.Giveaway;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.repositories.GiveawayRepository;
import com.diasandre.oryn.repositories.SquadRepository;
import com.diasandre.oryn.repositories.UserRepository;
import com.diasandre.oryn.types.ErrorCode;
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
