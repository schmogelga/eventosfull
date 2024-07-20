package com.schmogel.eventosfull.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.schmogel.eventosfull.domain.model.UserInfo;

@Repository
public interface UsuarioRepository extends JpaRepository<UserInfo, UUID> {}
