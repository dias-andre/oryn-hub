package com.diasandre.oryn.repositories;

import com.diasandre.oryn.models.Proof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProofRepository extends JpaRepository<Proof, UUID> {
  List<Proof> findByGiveawayId(UUID giveawayId);
}
