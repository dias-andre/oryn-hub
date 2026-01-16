package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.UserService;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.dtos.user.GetUserTokenRequest;
import com.diasandre.oryn.dtos.user.LoginResponse;
import com.diasandre.oryn.dtos.user.UserSummaryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/discord/auth")
  public ResponseEntity<LoginResponse>
    authUser(@Valid @RequestBody GetUserTokenRequest body) {
    var result = this.userService.authUser(body.token());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/@me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<UserSummaryDTO> me(@AuthenticationPrincipal UUID userId) {
    return ResponseEntity.ok(this.userService.getUser(userId));
  }

  @GetMapping("/@me/squads")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<SquadSummaryDTO>> getUserSquads(@AuthenticationPrincipal UUID userId) {
    return ResponseEntity.ok(this.userService.getUserSquads(userId));
  }

  @DeleteMapping("/@me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UUID userId) {
    this.userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
