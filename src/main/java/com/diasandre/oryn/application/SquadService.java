package com.diasandre.oryn.application;

import com.fasterxml.uuid.Generators;
import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.exceptions.NotFoundException;
import com.diasandre.oryn.mappers.MemberMapper;
import com.diasandre.oryn.mappers.SquadMapper;
import com.diasandre.oryn.models.Member;
import com.diasandre.oryn.models.MemberId;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.repositories.MemberRepository;
import com.diasandre.oryn.repositories.SquadRepository;
import com.diasandre.oryn.repositories.UserRepository;
import com.diasandre.oryn.types.SquadRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SquadService {
  private final SquadRepository squadRepository;
  private final MemberRepository memberRepository;
  private final UserRepository userRepository;

  @Transactional
  public SquadSummaryDTO createSquad(String squadName, UUID userId) {
    User user = this.userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found!"));
    Squad squad = new Squad();
    squad.setId(Generators.timeBasedEpochGenerator().generate());
    squad.setName(squadName);

    squad.setMembers(new ArrayList<>());

    MemberId memberId = new MemberId(user.getId(), squad.getId());
    Member owner = new Member();
    owner.setId(memberId);
    owner.setUser(user);
    owner.setSquad(squad);
    owner.setJoinedAt(OffsetDateTime.now());
    owner.setRole(SquadRole.OWNER);

    squad.getMembers().add(owner);

    this.squadRepository.save(squad);

    return SquadMapper.toSummary(squad);
  }
  
  public List<MemberSummaryDTO> getSquadMembers(UUID squadId) {
    if(!this.squadRepository.existsById(squadId)) {
      throw new NotFoundException("Squad not found!");
    }

    var members = this.memberRepository.findBySquadId(squadId);
    return members.stream().map(MemberMapper::toSummary).toList();
  }

  public void deleteSquad(UUID squadId) {
    if(this.squadRepository.existsById(squadId)) {
      this.squadRepository.deleteById(squadId);
    }
  }

  public SquadSummaryDTO getSquadById(UUID squadId) {
    return SquadMapper.toSummary(this.squadRepository.findById(squadId)
            .orElseThrow(() -> new NotFoundException("Squad not found!")));
  }
}
