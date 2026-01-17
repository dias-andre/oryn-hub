package com.diasandre.oryn.controllers;

import com.diasandre.oryn.application.UserService;
import com.diasandre.oryn.dtos.squad.SquadSummaryDTO;
import com.diasandre.oryn.dtos.user.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/discord/register")
  @ApiResponse(responseCode = "201")
  public ResponseEntity<LoginResponse> createUserAccount(@RequestBody SignUpWithDiscord body) {
    return ResponseEntity.status(201).body(this.userService.createUser(body));
  }

  @PostMapping("/register")
  @Operation(summary = "Create blank user with email and password")
  @ApiResponse(responseCode = "201")
  public ResponseEntity<LoginResponse> createUserWithPassword(@RequestBody CreateUserByPassword body) {
    return ResponseEntity.status(201).body(this.userService.createUser(body));
  }

  @PostMapping("/auth")
  @Operation(summary = "Authenticate user using email and password")
  public ResponseEntity<LoginResponse> authUserByPassword(@RequestBody EmailAndPasswordRequest body) {
    return ResponseEntity.ok(this.userService.authByPassword(body));
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
  @ApiResponse(responseCode = "204")
  public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UUID userId) {
    this.userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}
