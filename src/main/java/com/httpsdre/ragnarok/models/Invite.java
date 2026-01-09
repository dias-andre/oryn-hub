package com.httpsdre.ragnarok.models;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "invites")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Invite {
  @Id
  private UUID id;

  @Column(unique = true)
  private String code;
  private Integer usageCount;
  private Integer usageLimit;
  @Column(updatable = false)
  private OffsetDateTime createdAt;
  private OffsetDateTime expiresAt;
  private boolean isPaused;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private User author;

  @ManyToOne
  @JoinColumn(name = "squad_id")
  private Squad squad;

  @PrePersist
  private void prepare() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
    if(this.code == null || this.code.isEmpty()) {
      this.code = this.generateRandomCode();
    }
    if(this.createdAt == null) {
      this.createdAt = OffsetDateTime.now();
    }
  }

  public void pause() {
    this.isPaused = true;
  }

  public void unPause() {
    this.isPaused = false;
  }

  private String generateRandomCode() {
    String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    SecureRandom RANDOM = new SecureRandom();
    StringBuilder sb = new StringBuilder(20);
    for (int i = 0; i < 20; i++) {
      int index = RANDOM.nextInt(ALPHA_NUMERIC.length());
      sb.append(ALPHA_NUMERIC.charAt(index));
    }
    return sb.toString();
  }
}
