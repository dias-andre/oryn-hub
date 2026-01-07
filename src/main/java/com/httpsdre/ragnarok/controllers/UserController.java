package com.httpsdre.ragnarok.controllers;

import com.httpsdre.ragnarok.application.UserService;
import com.httpsdre.ragnarok.dtos.user.GetUserTokenRequest;
import com.httpsdre.ragnarok.dtos.user.LoginResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/discord/auth")
  public ResponseEntity<LoginResponse>
    authUser(@Valid @RequestBody GetUserTokenRequest body) {
    return ResponseEntity.ok(this.userService.authAndCreateUser(body.token()));
  }
}
