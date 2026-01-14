package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.GiveawayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/giveaways")
@RequiredArgsConstructor
public class GiveawayController {
  private final GiveawayService giveawayService;

  @DeleteMapping("/{giveawayId}")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfGiveawaySquad(giveawayId, principal)")
  public ResponseEntity<Void> deleteGiveaway(@PathVariable UUID giveawayId) {
    this.giveawayService.deleteGiveawayById(giveawayId);
    return ResponseEntity.noContent().build();
  }
}
