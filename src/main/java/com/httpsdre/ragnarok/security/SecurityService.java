package com.httpsdre.ragnarok.security;

import com.httpsdre.ragnarok.models.MemberId;
import com.httpsdre.ragnarok.repositories.InviteRepository;
import com.httpsdre.ragnarok.repositories.MemberRepository;
import com.httpsdre.ragnarok.types.SquadRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
  private final MemberRepository memberRepository;
  private final InviteRepository inviteRepository;

  public boolean isMemberOf(UUID squadId, UUID userId) {
    return memberRepository.existsById(new MemberId(userId, squadId));
  }

  public boolean isOwnerOf(UUID squadId, UUID userId) {
    return memberRepository.findById(new MemberId(userId, squadId))
            .map(m -> m.getRole() == SquadRole.OWNER)
            .orElse(false);
  }

  public boolean isMemberOfInviteSquad(UUID inviteCode, UUID userId) {
    return inviteRepository.isUserAlreadyInSquadByInviteCode(inviteCode, userId);
  }
}
