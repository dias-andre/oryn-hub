package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.InviteService;
import com.diasandre.oryn.dtos.member.MemberSummaryDTO;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
@Tag(name = "invites")
public class InviteController {
  private final InviteService inviteService;

  @GetMapping("/{inviteCode}")
  @Operation(summary = "Get squad details by invite code")
  public ResponseEntity<SquadSummaryDTO> checkServerByInviteCode(@PathVariable String inviteCode) {
    var squad = this.inviteService.fetchSquadByInviteCode(inviteCode);
    return ResponseEntity.ok(squad);
  }

  @PostMapping("/{inviteCode}/accept")
  @Operation(summary = "Create membership using invite code")
  public ResponseEntity<MemberSummaryDTO>
    acceptInvite(@PathVariable String inviteCode, @AuthenticationPrincipal User user) {
    var memberDetails = this.inviteService.acceptInvite(inviteCode, user);
    return ResponseEntity.ok(memberDetails);
  }

  @Operation(summary = "Pause invite")
  @PatchMapping("/{inviteId}/pause")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfInviteSquad(inviteId, principal)")
  public ResponseEntity<Void>
    pauseInvite(@PathVariable UUID inviteId) {
    this.inviteService.pause(inviteId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{inviteId}/resume")
  @Operation(summary = "Resume invite")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfInviteSquad(inviteId, principal)")
  public ResponseEntity<Void>
    resumeInvite(@PathVariable UUID inviteId) {
    this.inviteService.resume(inviteId);
    return ResponseEntity.noContent().build();
  }
}
