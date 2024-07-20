package com.schmogel.eventosfull.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record PresencaResponse(UUID id, LocalDateTime dataHoraCheckin, UUID inscricaoId) {}
