package com.diasandre.oryn.application;

import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.exceptions.BusinessException;
import com.diasandre.oryn.mappers.MemberMapper;
import com.diasandre.oryn.models.Member;
import com.diasandre.oryn.models.MemberId;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.repositories.MemberRepository;
import com.diasandre.oryn.repositories.SquadRepository;
import com.diasandre.oryn.types.SquadRole;
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
  public MemberSummaryDTO pushMemberToSquad(UUID squadId, User user) {
    Squad squadProxy = this.squadRepository.getReferenceById(squadId);
    boolean userHasSubscription = this.memberRepository
            .existsById(new MemberId(user.getId(), squadId));

    if(userHasSubscription) {
      throw new BusinessException("This user is already a member!");
    }

    Member newMember = new Member();
    newMember.setSquad(squadProxy);
    newMember.setUser(user);
    newMember.setJoinedAt(OffsetDateTime.now());
    newMember.setRole(SquadRole.USER);
    newMember = this.memberRepository.save(newMember);
    return MemberMapper.toSummary(newMember);
  }
}
