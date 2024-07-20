package com.schmogel.eventosfull.application.dto;

import java.util.UUID;

public record InscricaoRequest(UUID usuarioId, UUID eventoId) {}
