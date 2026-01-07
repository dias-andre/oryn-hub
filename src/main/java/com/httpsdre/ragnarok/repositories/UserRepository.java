package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByDiscordId(String discordId);
  boolean existsByDiscordId(String discordId);

  User getReferenceByDiscordId(@NotBlank String id);
}
