package com.httpsdre.ragnarok.models;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@SoftDelete(columnName = "is_deleted")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  private UUID id;

  @Column(unique = true)
  private String discordId;
  private String displayName;
  private String username;
  @Column(unique = true)
  private String email;
  private String avatar;

//  private boolean isDeleted;
  @Column(updatable = false)
  private OffsetDateTime createdAt;
  private OffsetDateTime lastLogin;

  @OneToMany(mappedBy = "user")
  private List<Member> members;

  public User(String discordId, String displayName, String username, String email, String avatar, OffsetDateTime createdAt, OffsetDateTime lastLogin) {
    this.id = Generators.timeBasedEpochGenerator().generate();
    this.discordId = discordId;
    this.displayName = displayName;
    this.username = username;
    this.email = email;
    this.avatar = avatar;
    this.createdAt = createdAt;
    this.lastLogin = lastLogin;
  }

  @PrePersist
  protected void onCreate() {
    if(this.id == null) {
      this.id = Generators.timeBasedEpochGenerator().generate();
    }
  }
}
