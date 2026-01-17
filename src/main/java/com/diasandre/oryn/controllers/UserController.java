package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.UserService;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.dtos.user.GetUserTokenRequest;
import com.diasandre.oryn.dtos.user.LoginResponse;
import com.diasandre.oryn.dtos.user.SignUpWithDiscord;
import com.diasandre.oryn.dtos.user.UserSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "users")
public class UserController {
  private final UserService userService;

  @PostMapping("/discord/auth")
  @Operation(summary = "Authenticate user by discord token")
  public ResponseEntity<LoginResponse>
    authUser(@Valid @RequestBody GetUserTokenRequest body) {
    var result = this.userService.authUser(body.token());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/@me")
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "Get user profile using JWT")
  public ResponseEntity<UserSummaryDTO> me(@AuthenticationPrincipal UUID userId) {
    return ResponseEntity.ok(this.userService.getUser(userId));
  }

  @GetMapping("/@me/squads")
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "Get user squads using JWT")
  public ResponseEntity<List<SquadSummaryDTO>> getUserSquads(@AuthenticationPrincipal UUID userId) {
    return ResponseEntity.ok(this.userService.getUserSquads(userId));
  }

  @DeleteMapping("/@me")
  @PreAuthorize("isAuthenticated()")
  @Operation(summary = "Delete user account using JWT")
  public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UUID userId) {
    this.userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/discord/register")
  public ResponseEntity<LoginResponse> createUserAccount(@RequestBody SignUpWithDiscord body) {
    return ResponseEntity.status(201).body(this.userService.createUser(body));
  }
}
