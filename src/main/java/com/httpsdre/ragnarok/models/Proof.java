package com.httpsdre.ragnarok.models;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "proofs")
@Getter @Setter
public class Proof {
  @Id
  private UUID id;

  @Column(columnDefinition = "text")
  private String fileKey;
  @Column(columnDefinition = "text")
  private String originalName;
  private String contentType;

  @Column(length = 500)
  private String description;

  @Transient
  private String url;

  @Column(updatable = false)
  private OffsetDateTime createdAt;

  @ManyToOne
  @JoinColumn(name = "giveaway_id")
  private Giveaway giveaway;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @PrePersist
  private void prepare() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
  }

  public String getUrl() {
    return "";
  }
}
