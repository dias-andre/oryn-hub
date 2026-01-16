package com.diasandre.oryn.models;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "giveaways")
@SoftDelete(columnName = "is_deleted")
@Getter @Setter
public class Giveaway {
  @Id
  private UUID id;

  private String title;
  @Column(columnDefinition = "text")
  private String prizeDescription;
  private OffsetDateTime startsAt;
  private OffsetDateTime endsAt;
  @Column(insertable = false)
  private OffsetDateTime deletedAt;
  @Column(updatable = false)
  private OffsetDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "squad_id")
  private Squad squad;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @PrePersist
  public void prepare() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
    if(this.createdAt == null) {
      this.createdAt = OffsetDateTime.now();
    }
  }
}
