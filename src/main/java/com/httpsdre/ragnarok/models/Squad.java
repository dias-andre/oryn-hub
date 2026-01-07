package com.httpsdre.ragnarok.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @OneToMany(mappedBy = "squad")
  private List<Member> members;
}
