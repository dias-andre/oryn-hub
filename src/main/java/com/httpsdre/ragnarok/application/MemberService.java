package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.mappers.MemberMapper;
import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.Squad;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.repositories.MemberRepository;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.types.SquadRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {
  private final SquadRepository squadRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public MemberSummaryDTO createSquadMember(UUID squadId, User user) {
    Squad squad = this.squadRepository.findById(squadId)
            .orElseThrow(() -> new NotFoundException("Squad with Id " + squadId.toString() + " not found!"));
    Member newMember = new Member();
    newMember.setSquad(squad);
    newMember.setUser(user);
    newMember.setJoinedAt(OffsetDateTime.now());
    newMember.setRole(SquadRole.USER);
    newMember = this.memberRepository.save(newMember);
    return MemberMapper.toSummary(newMember);
  }

  @Transactional
  public MemberSummaryDTO pushMemberToSquad(UUID squadId, User user) {
    Squad squadProxy = this.squadRepository.getReferenceById(squadId);
    Member newMember = new Member();
    newMember.setSquad(squadProxy);
    newMember.setUser(user);
    newMember.setJoinedAt(OffsetDateTime.now());
    newMember.setRole(SquadRole.USER);
    newMember = this.memberRepository.save(newMember);
    return MemberMapper.toSummary(newMember);
  }
}
