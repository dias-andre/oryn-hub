package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.InviteService;
import com.httpsdre.ragnarok.application.MemberService;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invites")
@RequiredArgsConstructor
public class InviteController {
  private final MemberService memberService;
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
}
