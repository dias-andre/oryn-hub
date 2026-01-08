package com.httpsdre.ragnarok.application;

import com.httpsdre.ragnarok.dtos.invite.InviteSummaryDTO;
import com.httpsdre.ragnarok.dtos.member.InviteAuthorDTO;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.exceptions.NotFoundException;
import com.httpsdre.ragnarok.exceptions.UnprocessableEntityException;
import com.httpsdre.ragnarok.mappers.InviteMapper;
import com.httpsdre.ragnarok.mappers.MemberMapper;
import com.httpsdre.ragnarok.models.*;
import com.httpsdre.ragnarok.repositories.InviteRepository;
import com.httpsdre.ragnarok.repositories.MemberRepository;
import com.httpsdre.ragnarok.repositories.SquadRepository;
import com.httpsdre.ragnarok.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InviteService {
  private final SquadRepository squadRepository;
  private final InviteRepository inviteRepository;
  private final MemberRepository memberRepository;

  @Transactional
  public InviteSummaryDTO createInvite(UUID squadId, User author, LocalDateTime expires, Integer usageLimit) {
    Squad squadProxy = this.squadRepository.getReferenceById(squadId);
    Invite newInvite = new Invite(null, null, 0, usageLimit, LocalDateTime.now(), expires, false, author, squadProxy);
    newInvite = this.inviteRepository.save(newInvite);

    var authorSummary = new InviteAuthorDTO(author.getId(), author.getDisplayName(), author.getUsername(), author.getAvatar());
    InviteSummaryDTO dto = InviteMapper.toSummary(newInvite, authorSummary);
    return dto;
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

  @Transactional
  public void updateInvitePause(UUID inviteId, boolean newValue) {
    Invite invite = this.inviteRepository.findById(inviteId)
            .orElseThrow(() -> new NotFoundException("Invite with id " + inviteId.toString() + " not found!"));
    if(newValue) {
      invite.pause();
    }
    invite.unPause();
  }
}
