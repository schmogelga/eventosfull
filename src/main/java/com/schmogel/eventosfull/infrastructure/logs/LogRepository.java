package com.schmogel.eventosfull.infrastructure.logs;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<LogInfo, UUID> {}
