package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface InviteRepository extends JpaRepository<Invite, UUID> {
  @Query("""
    SELECT i FROM Invite i
    JOIN FETCH i.author
    JOIN FETCH i.squad
    WHERE i.squad.id = :squadId
    """)
  List<Invite> findAllWithDetails(UUID squadId);
}
