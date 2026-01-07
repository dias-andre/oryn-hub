package com.httpsdre.ragnarok.repositories;

import com.httpsdre.ragnarok.models.Member;
import com.httpsdre.ragnarok.models.MemberId;
import com.httpsdre.ragnarok.models.Squad;
import com.httpsdre.ragnarok.models.User;
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
