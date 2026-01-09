package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InviteRepository extends JpaRepository<Invite, UUID> {
  @Query("""
    SELECT i FROM Invite i
    JOIN FETCH i.author
    JOIN FETCH i.squad
    WHERE i.squad.id = :squadId
    ORDER BY i.id ASC
    """)
  List<Invite> findAllWithDetails(UUID squadId);

  @Query("""
    SELECT i FROM Invite i
    JOIN FETCH i.author
    JOIN FETCH i.squad
    WHERE i.author.id = :userId AND i.squad.id = :squadId
    ORDER BY i.id ASC
    """)
  List<Invite> filterByUserAndSquadId(UUID userId, UUID squadId);
  Optional<Invite> findByCode(String code);

  @Query("""
    SELECT COUNT(m) > 0
    FROM Invite i
    JOIN Member m ON m.squad = i.squad
    WHERE i.id = :code AND m.user.id = :userId
    """)
  boolean isUserAlreadyInSquadByInviteCode(UUID code, UUID userId);
}
