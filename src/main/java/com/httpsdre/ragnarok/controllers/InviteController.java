package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.InviteService;
import com.httpsdre.ragnarok.application.MemberService;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
public class InviteController {
  private final InviteService inviteService;

  @GetMapping("/{inviteCode}")
  public ResponseEntity<SquadSummaryDTO> checkServerByInviteCode(@PathVariable String inviteCode) {
    var squad = this.inviteService.fetchSquadByInviteCode(inviteCode);
    return ResponseEntity.ok(squad);
  }

  @PostMapping("/{inviteCode}/accept")
  public ResponseEntity<MemberSummaryDTO>
    acceptInvite(@PathVariable String inviteCode, @AuthenticationPrincipal User user) {
    var memberDetails = this.inviteService.acceptInvite(inviteCode, user);
    return ResponseEntity.ok(memberDetails);
  }

  @PatchMapping("/{inviteId}/pause")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfInviteSquad(inviteId, principal)")
  public ResponseEntity<Void>
    pauseInvite(@PathVariable UUID inviteId) {
    this.inviteService.pause(inviteId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{inviteId}/resume")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfInviteSquad(inviteId, principal)")
  public ResponseEntity<Void>
    resumeInvite(@PathVariable UUID inviteId) {
    this.inviteService.resume(inviteId);
    return ResponseEntity.noContent().build();
  }
}
