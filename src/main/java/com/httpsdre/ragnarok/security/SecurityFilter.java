package com.httpsdre.ragnarok.security;

import com.httpsdre.ragnarok.application.TokenService;
import com.httpsdre.ragnarok.exceptions.UnauthorizedException;
import com.httpsdre.ragnarok.models.User;
import com.httpsdre.ragnarok.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if(token != null) {
      String userIdString = tokenService.validateToken(token);
      if(!userIdString.isEmpty()) {
        User user = userRepository.getReferenceById(UUID.fromString(userIdString));
        var authentication = new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
    return authHeader.replace("Bearer ", "");
  }
}
