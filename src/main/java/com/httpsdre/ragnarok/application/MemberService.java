package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.exceptions.BusinessException;
import com.httpsdre.ragnarok.mappers.MemberMapper;
import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.MemberId;
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
