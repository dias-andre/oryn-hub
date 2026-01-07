package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.UserService;
import com.httpsdre.ragnarok.dtos.user.GetUserTokenRequest;
import com.httpsdre.ragnarok.dtos.user.LoginResponse;
import com.httpsdre.ragnarok.dtos.user.UserSummaryDTO;
import com.httpsdre.ragnarok.models.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/discord/auth")
  public ResponseEntity<LoginResponse>
  authUser(@Valid @RequestBody GetUserTokenRequest body) {
    var result = this.userService.authAndCreateUser(body.token());
    return ResponseEntity.ok(result);
  }

  @GetMapping("/@me")
  public ResponseEntity<UserSummaryDTO> me(@AuthenticationPrincipal User user) {
    return ResponseEntity.ok(this.userService.toSummary(user));
  }
}
