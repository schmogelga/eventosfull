package com.schmogel.eventosfull.application.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record InscricaoResponse(UUID id, UUID usuarioId, UUID eventoId) {}
