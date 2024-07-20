package com.schmogel.eventosfull.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "usuario")
public class UserInfo {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    private String username;
    private String nome;
    private String email;

    @OneToMany(mappedBy = "userInfo")
    @JsonIgnore
    private List<Inscricao> inscricoes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> roles = new HashSet<>();
}
