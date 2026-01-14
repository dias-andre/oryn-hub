package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.Proof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProofRepository extends JpaRepository<Proof, UUID> {
}
