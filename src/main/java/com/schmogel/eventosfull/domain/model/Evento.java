package com.schmogel.eventosfull.domain.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    private String nome;
    private LocalDate data;

    @OneToMany(mappedBy = "evento", fetch = FetchType.EAGER)
    private List<Inscricao> inscricoes;
}
