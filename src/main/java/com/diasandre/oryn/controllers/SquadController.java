package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.GiveawayService;
import com.diasandre.oryn.application.InviteService;
import com.diasandre.oryn.application.SquadService;
import com.diasandre.oryn.dtos.giveaway.CreateGiveawayRequest;
import com.diasandre.oryn.dtos.giveaway.GiveawaySummaryDTO;
import com.diasandre.oryn.dtos.invite.CreateInviteRequest;
import com.diasandre.oryn.dtos.invite.InviteSummaryDTO;
import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.dtos.squad.CreateSquadRequest;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/squads")
@RequiredArgsConstructor
@Tag(name = "Squad manager")
public class SquadController {
  private final SquadService squadService;
  private final InviteService inviteService;
  private final GiveawayService giveawayService;

  @PostMapping
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "Create squad")
  public ResponseEntity<SquadSummaryDTO> createSquad(@RequestBody CreateSquadRequest body,
                                                     @AuthenticationPrincipal UUID userId) {
    var created = this.squadService.createSquad(body.name(), userId);
    return ResponseEntity.status(201).body(created);
  }

  @PreAuthorize("isAuthenticated() and @securityService.isOwnerOf(#squadId, principal)")
  @DeleteMapping("/{squadId}")
  @Operation(summary = "Delete squad")
  public ResponseEntity<Void> deleteSquad(@PathVariable UUID squadId) {
    this.squadService.deleteSquad(squadId);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @GetMapping("/{squadId}")
  @Operation(summary = "Get squad by Id")
  public ResponseEntity<SquadSummaryDTO> getSquadById(@PathVariable UUID squadId) {
    return ResponseEntity.ok(this.squadService.getSquadById(squadId));
  }

  /* Members */

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#id, principal)")
  @GetMapping("/{id}/members")
  @Operation(summary = "Get squad members")
  public ResponseEntity<List<MemberSummaryDTO>> getSquadMembers(@PathVariable UUID id) {
    var members = this.squadService.getSquadMembers(id);
    return ResponseEntity.ok(members);
  }

  /* Invites */
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @GetMapping("/{squadId}/members/{userId}/invites")
  @Operation(summary = "List invites from squad member", tags = {"invites"})
  public ResponseEntity<List<InviteSummaryDTO>> getSquadMemberInvites(
          @PathVariable UUID squadId,
          @PathVariable UUID userId
  ) {
    var invitesData = this.inviteService.getSquadMemberInvites(squadId, userId);
    return ResponseEntity.ok(invitesData);
  }

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @PostMapping("/{squadId}/invites")
  @Operation(summary = "Create invite", tags = {"invites"})
  public ResponseEntity<InviteSummaryDTO>
  createInvite(@PathVariable UUID squadId, @RequestBody CreateInviteRequest body,
               @AuthenticationPrincipal UUID authorId) {
    var invitesData = this.inviteService
            .createInvite(squadId, authorId, body.expiresAt(), body.usageLimit());
    return ResponseEntity.status(201).body(invitesData);
  }

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @GetMapping("/{squadId}/invites")
  @Operation(summary = "List all squad invites", tags = {"invites"})
  public ResponseEntity<List<InviteSummaryDTO>> getSquadInvites(@PathVariable UUID squadId, Pageable page) {
    var invites = this.inviteService.getSquadInvites(squadId, page);
    return ResponseEntity.ok(invites);
  }

  /* Giveaways */

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @PostMapping("/{squadId}/giveaways")
  @Operation(summary = "Register squad giveaways", tags = {"giveaways"})
  public ResponseEntity<GiveawaySummaryDTO>
  createGiveaway(@PathVariable UUID squadId, @RequestBody CreateGiveawayRequest body,
                 @AuthenticationPrincipal UUID authorId) {
    var created = this.giveawayService.createGiveaway(authorId, squadId, body);
    return ResponseEntity.status(201).body(created);
  }

  @PreAuthorize("isAuthenticated() and @securityService.isMemberOf(#squadId, principal)")
  @GetMapping("/{squadId}/giveaways")
  @Operation(summary = "List squad giveaways", tags = {"giveaways"})
  public ResponseEntity<List<GiveawaySummaryDTO>>
  getSquadGiveaways(@PathVariable UUID squadId, @RequestParam(required = false) UUID lastId,
                    @RequestParam(defaultValue = "10") int pageSize) {
    var list = this.giveawayService.getSquadGiveaways(squadId, lastId, pageSize);
    return ResponseEntity.ok(list);
  }
}
