package com.httpsdre.ragnarok.models;

import com.httpsdre.ragnarok.types.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "squad_members")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
  @EmbeddedId
  private MemberId id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("squadId")
  @JoinColumn(name = "squad_id")
  private Squad squad;

  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Column(updatable = false)
  private LocalDateTime joinedAt;
}
