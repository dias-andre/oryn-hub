package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.Squad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SquadRepository extends JpaRepository<Squad, UUID> {
  @Query("""
    SELECT DISTINCT s FROM Squad s
    INNER JOIN FETCH s.members m
    WHERE m.user.id = :userId
    """)
  List<Squad> getUserSquads(UUID userId);
}
