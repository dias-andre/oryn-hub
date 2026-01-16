package com.diasandre.oryn.repositories;

import com.diasandre.oryn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  Optional<User> findByDiscordId(String discordId);
  Optional<User> findByEmail(String email);

  @Modifying
  @Query(value = """
    UPDATE users SET is_deleted = false 
    WHERE id = :userId
    """, nativeQuery = true)
  void activateUser(UUID userId);
}
