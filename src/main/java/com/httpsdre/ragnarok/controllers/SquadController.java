package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.SquadService;
import com.httpsdre.ragnarok.dtos.member.MemberSummaryDTO;
import com.httpsdre.ragnarok.dtos.squad.CreateSquadRequest;
import com.httpsdre.ragnarok.dtos.squad.SquadSummaryDTO;
import com.httpsdre.ragnarok.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/squads")
@RequiredArgsConstructor
public class SquadController {
  private final SquadService squadService;

  @PostMapping
  public ResponseEntity<SquadSummaryDTO> createSquad(@RequestBody CreateSquadRequest body,
                                                     @AuthenticationPrincipal User user) {
    var created = this.squadService.createSquad(body.name(), user);
    return ResponseEntity.status(201).body(created);
  }

  @GetMapping("/{id}/members")
  public ResponseEntity<List<MemberSummaryDTO>> getSquadMembers(@PathVariable String id) {
    var members = this.squadService.getSquadMembers(UUID.fromString(id));
    return ResponseEntity.ok(members);
  }
}
