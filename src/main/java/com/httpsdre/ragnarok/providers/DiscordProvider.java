package com.httpsdre.ragnarok.providers;

import com.httpsdre.ragnarok.dtos.GetCurrentUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DiscordProvider {
  private final RestClient restClient;

  public DiscordProvider(RestClient.Builder builder) {
    this.restClient = builder.baseUrl("https://discord.com/api/").build();
  }
  public GetCurrentUserRequest getCurrentUser(String token) {
    return restClient.get()
            .uri("/users/@me")
            .headers(h -> h.setBearerAuth(token))
            .retrieve()
            .body(GetCurrentUserRequest.class);
  }
}
