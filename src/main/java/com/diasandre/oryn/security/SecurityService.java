package com.diasandre.oryn.security;

import com.diasandre.oryn.models.MemberId;
import com.diasandre.oryn.repositories.GiveawayRepository;
import com.diasandre.oryn.repositories.InviteRepository;
import com.diasandre.oryn.repositories.MemberRepository;
import com.diasandre.oryn.types.SquadRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
  private final MemberRepository memberRepository;
  private final InviteRepository inviteRepository;
  private final GiveawayRepository giveawayRepository;

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

  public boolean isMemberOfGiveawaySquad(UUID giveawayId, UUID userId) {
    return giveawayRepository.isMemberOfGiveawaySquad(userId, giveawayId);
  }
}
