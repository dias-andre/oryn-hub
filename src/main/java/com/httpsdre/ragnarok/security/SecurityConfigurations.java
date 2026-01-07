package com.httpsdre.ragnarok.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

  private final SecurityFilter securityFilter;

  public SecurityConfigurations(SecurityFilter securityFilter) {
    this.securityFilter = securityFilter;
  }

  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                    session.sessionCreationPolicy((SessionCreationPolicy.STATELESS))
            )
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.POST, "/users/discord/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users/discord/register").permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
  }
}
