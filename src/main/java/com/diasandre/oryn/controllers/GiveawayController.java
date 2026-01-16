package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.GiveawayService;
import com.diasandre.oryn.application.ProofService;
import com.diasandre.oryn.dtos.proof.ProofSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/giveaways")
@RequiredArgsConstructor
@Tag(name = "giveaways")
public class GiveawayController {
  private final GiveawayService giveawayService;
  private final ProofService proofService;

  @DeleteMapping("/{giveawayId}")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfGiveawaySquad(giveawayId, principal)")
  @Operation(summary = "Delete giveaway")
  public ResponseEntity<Void> deleteGiveaway(@PathVariable UUID giveawayId) {
    this.giveawayService.deleteGiveawayById(giveawayId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/{giveawayId}/proofs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfGiveawaySquad(#giveawayId, principal)")
  @Operation(summary = "Upload giveaway proof")
  public ResponseEntity<ProofSummaryDTO> uploadProof(
          @RequestParam("file") MultipartFile file,
          @PathVariable UUID giveawayId,
          @AuthenticationPrincipal UUID authorId,
          @RequestParam(value = "description", required = false) String description
          ) {
    var savedProof = proofService.processAndUpload(file, giveawayId, authorId, description);
    return ResponseEntity.status(201).body(savedProof);
  }

  @GetMapping("/{giveawayId}/proofs")
  @PreAuthorize("isAuthenticated() and @securityService.isMemberOfGiveawaySquad(#giveawayId, principal)")
  @Operation(summary = "List giveaways proofs")
  public ResponseEntity<List<ProofSummaryDTO>>
    getGiveawayProofs(@PathVariable UUID giveawayId) {
    var result = this.proofService.getGiveawayProofs(giveawayId);
    return ResponseEntity.ok(result);
  }
}
