package com.httpsdre.ragnarok.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.httpsdre.ragnarok.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TokenService {
  @Value("${api.security.token.secret}")
  private String secret;

  public String generateUserToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()
              .withIssuer("ragnarok-api")
              .withSubject(user.getId().toString())
              .withExpiresAt(this.genExpirationDate())
              .sign(algorithm);
    } catch (JWTCreationException e) {
      throw new RuntimeException("Erro ao gerar token JWT ", e);
    }
  }

  private Instant genExpirationDate() {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
              .withIssuer("ragnarok-api") // Valida se foi você mesmo que emitiu
              .build()
              .verify(token) // Decodifica
              .getSubject(); // Pega o ID que você salvou lá dentro
    } catch (JWTVerificationException exception) {
      // Se o token for inválido, expirado ou adulterado, cai aqui.
      // Retornamos vazio para o SecurityFilter saber que falhou.
      return null;
    }
  }
}
