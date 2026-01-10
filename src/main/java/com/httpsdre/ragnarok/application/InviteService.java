package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.invite.InviteSummaryDTO;
import com.httpsdre.ragnarok.dtos.member.InviteAuthorDTO;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.BusinessException;
import com.httpsdre.ragnarok.mappers.InviteMapper;
import com.httpsdre.ragnarok.mappers.SquadMapper;
import com.httpsdre.ragnarok.models.*;
import com.httpsdre.ragnarok.repositories.InviteRepository;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
import com.httpsdre.ragnarok.types.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
  private final SquadRepository squadRepository;
  private final InviteRepository inviteRepository;
  private final MemberService memberService;
  private final UserRepository userRepository;

  @Transactional
  public InviteSummaryDTO createInvite(UUID squadId, UUID authorId, OffsetDateTime expires, Integer usageLimit) {
    User author = this.userRepository.findById(authorId)
            .orElseThrow(() -> new NotFoundException("User not found!"));
    Squad squadProxy = this.squadRepository.getReferenceById(squadId);
    Invite newInvite = new Invite(null, null, 0, usageLimit, OffsetDateTime.now(), expires, false, author, squadProxy);
    newInvite = this.inviteRepository.save(newInvite);

    var authorSummary = new InviteAuthorDTO(author.getId(), author.getDisplayName(), author.getUsername(), author.getAvatar());
    return InviteMapper.toSummary(newInvite, authorSummary);
  }

  public List<InviteSummaryDTO> getSquadInvites(UUID squadId) {
    List<Invite> invites = this.inviteRepository.findAllWithDetails(squadId);
    return invites.stream()
            .map(i -> {
              User author = i.getAuthor();
              return InviteMapper.toSummary(i,
                      new InviteAuthorDTO(author.getId(), author.getDisplayName(),
                              author.getUsername(), author.getAvatar())
              );
            }).toList();
  }

  public List<InviteSummaryDTO> getSquadMemberInvites(UUID squadId, UUID userId) {
    List<Invite> invites = this.inviteRepository.filterByUserAndSquadId(userId, squadId);
    return invites.stream()
            .map(i -> {
              User author = i.getAuthor();
              return InviteMapper.toSummary(i,
                      new InviteAuthorDTO(author.getId(), author.getDisplayName(),
                              author.getUsername(), author.getAvatar())
              );
            }).toList();
  }

  @Transactional
  public MemberSummaryDTO acceptInvite(String inviteCode, User user) {
    Invite invite = this.inviteRepository.findByCode(inviteCode)
            .orElseThrow(() -> new NotFoundException("Invite not found!"));

    if(invite.isPaused()) {
      throw new BusinessException(ErrorCode.INVITE_PAUSED);
    }

    var usageCount = invite.getUsageCount();
    var usageLimit = invite.getUsageLimit();

    if (usageLimit != null && usageCount >= usageLimit) {
      throw new BusinessException(ErrorCode.INVITE_LIMIT_EXCEEDED);
    }
    usageCount++;
    invite.setUsageCount(usageCount);
    return this.memberService
            .pushMemberToSquad(invite.getSquad().getId(), user);
  }

  public SquadSummaryDTO fetchSquadByInviteCode(String inviteCode) {
    Invite invite = this.inviteRepository.findByCode(inviteCode)
            .orElseThrow(() -> new NotFoundException("Invite with code " + inviteCode + " not found!"));
    return SquadMapper.toSummary(invite.getSquad());
  }

  @Transactional
  public void pause(UUID inviteId) {
    Invite invite = this.inviteRepository.findById(inviteId)
            .orElseThrow(() -> new NotFoundException("Invite with Id " + inviteId.toString() + " not found!"));
    invite.setPaused(true);
  }

  @Transactional
  public void resume(UUID inviteId) {
    Invite invite = this.inviteRepository.findById(inviteId)
            .orElseThrow(() -> new NotFoundException("Invite with Id " + inviteId.toString() + "not found!"));
    invite.setPaused(false);
  }
}
