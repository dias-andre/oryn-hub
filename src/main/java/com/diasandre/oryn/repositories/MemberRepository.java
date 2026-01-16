package com.diasandre.oryn.repositories;

import com.diasandre.oryn.models.Member;
import com.diasandre.oryn.models.MemberId;
import com.diasandre.oryn.models.Squad;
import com.diasandre.oryn.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, MemberId> {
  List<Member> findByUser(User user);
  List<Member> findBySquad(Squad squad);
  List<Member> findBySquadId(UUID squadId);
}
