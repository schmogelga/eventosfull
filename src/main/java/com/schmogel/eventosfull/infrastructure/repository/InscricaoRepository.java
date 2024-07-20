package com.schmogel.eventosfull.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schmogel.eventosfull.domain.model.Inscricao;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {
    List<Inscricao> findAllByUserInfoId(UUID usuarioId);
}
