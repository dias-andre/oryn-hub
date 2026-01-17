package com.diasandre.oryn.application;

import com.diasandre.oryn.dtos.invite.InviteSummaryDTO;
import com.diasandre.oryn.dtos.member.InviteAuthorDTO;
import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.exceptions.NotFoundException;
import com.diasandre.oryn.exceptions.BusinessException;
import com.diasandre.oryn.mappers.InviteMapper;
import com.diasandre.oryn.mappers.SquadMapper;
import com.diasandre.oryn.models.Invite;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import com.diasandre.oryn.repositories.InviteRepository;
import com.diasandre.oryn.repositories.SquadRepository;
import com.diasandre.oryn.repositories.UserRepository;
import com.diasandre.oryn.types.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

  public List<InviteSummaryDTO> getSquadInvites(UUID squadId, Pageable pageable) {
    List<Invite> invites = this.inviteRepository.findBySquadIdOrderByIdDesc(squadId, pageable);
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
