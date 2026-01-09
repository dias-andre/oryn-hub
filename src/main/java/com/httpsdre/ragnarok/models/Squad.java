package com.httpsdre.ragnarok.models;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "squads")
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
  @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Member> members;

  @PrePersist
  protected void prepare() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
  }
}
