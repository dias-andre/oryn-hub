package com.httpsdre.ragnarok.models;

import com.fasterxml.uuid.Generators;
import com.httpsdre.ragnarok.types.SquadStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "squads")
@SoftDelete(columnName = "is_deleted")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Squad {
  @Id
  private UUID id;

  private String name;
  private String icon;
  private OffsetDateTime createdAt;
  private boolean isInvitesPaused;

  @Enumerated(EnumType.STRING)
  private SquadStatus status;
//  @Column(insertable = false)
//  private boolean isDeleted;
  private OffsetDateTime deletedAt;

  @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Member> members;

  @PrePersist
  protected void prepare() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
  }
}
