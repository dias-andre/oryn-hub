package com.diasandre.oryn.repositories;

import com.diasandre.oryn.models.Giveaway;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GiveawayRepository extends JpaRepository<Giveaway, UUID> {
  @Query("""
    SELECT g FROM Giveaway g
    JOIN FETCH g.author
    WHERE g.squad.id = :squadId
    AND (:lastId IS NULL OR g.id < :lastId)
    ORDER BY g.id DESC
    """)
  List<Giveaway> findBySquadId_Paged(UUID squadId, UUID lastId, Pageable pageable);
  @EntityGraph(attributePaths = {"author"})
  List<Giveaway> findBySquadIdOrderByIdDesc(UUID squadId, Pageable pageable);


  @Query("""
    SELECT COUNT(m) > 0
    FROM Member m
    JOIN Giveaway g on g.squad.id = m.squad.id
    WHERE g.id = :giveawayId
      AND m.user.id = :userId
  """)
  boolean isMemberOfGiveawaySquad(UUID userId, UUID giveawayId);
}
