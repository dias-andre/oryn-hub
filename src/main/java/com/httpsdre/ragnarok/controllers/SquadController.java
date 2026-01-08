package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.InviteService;
import com.httpsdre.ragnarok.application.SquadService;
import com.httpsdre.ragnarok.dtos.invite.CreateInviteRequest;
import com.httpsdre.ragnarok.dtos.invite.InviteSummaryDTO;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.CreateSquadRequest;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.models.Invite;
import com.httpsdre.ragnarok.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/squads")
@RequiredArgsConstructor
public class SquadController {
  private final SquadService squadService;
  private final InviteService inviteService;

  @PostMapping
  public ResponseEntity<SquadSummaryDTO> createSquad(@RequestBody CreateSquadRequest body,
                                                     @AuthenticationPrincipal User user) {
    var created = this.squadService.createSquad(body.name(), user);
    return ResponseEntity.status(201).body(created);
  }
  
  @PreAuthorize("@securityService.isMemberOf(#id, principal.id)")
  @GetMapping("/{id}/members")
  public ResponseEntity<List<MemberSummaryDTO>> getSquadMembers(@PathVariable UUID id) {
    var members = this.squadService.getSquadMembers(id);
    return ResponseEntity.ok(members);
  }

  @PreAuthorize("@securityService.isMemberOf(#squadId, principal.id)")
  @PostMapping("/{squadId}/invites")
  public ResponseEntity<InviteSummaryDTO>
    createInvite(@PathVariable UUID squadId, @RequestBody CreateInviteRequest body,
                 @AuthenticationPrincipal User author) {
    var invitesData = this.inviteService
            .createInvite(squadId, author, body.expiresAt(), body.usageLimit());
    return ResponseEntity.status(201).body(invitesData);
  }

  @PreAuthorize("@securityService.isMemberOf(#squadId, principal.id)")
  @GetMapping("/{squadId}/invites")
  public ResponseEntity<List<InviteSummaryDTO>> getSquadInvites(@PathVariable UUID squadId) {
    var invites = this.inviteService.getSquadInvites(squadId);
    return ResponseEntity.ok(invites);
  }
}
