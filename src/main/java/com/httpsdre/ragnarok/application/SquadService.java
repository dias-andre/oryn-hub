package com.httpsdre.ragnarok.application;

import com.fasterxml.uuid.Generators;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.mappers.MemberMapper;
import com.httpsdre.ragnarok.mappers.SquadMapper;
import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.MemberId;
import com.httpsdre.ragnarok.models.Squad;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.repositories.MemberRepository;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
import com.httpsdre.ragnarok.types.SquadRole;
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
