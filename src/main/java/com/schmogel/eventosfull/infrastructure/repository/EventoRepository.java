package com.schmogel.eventosfull.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schmogel.eventosfull.domain.model.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, UUID> {}
