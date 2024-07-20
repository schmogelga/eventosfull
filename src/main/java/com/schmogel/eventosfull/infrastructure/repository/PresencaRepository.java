package com.schmogel.eventosfull.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schmogel.eventosfull.domain.model.Presenca;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, UUID> {}
