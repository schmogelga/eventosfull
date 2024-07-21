package com.schmogel.eventosfull.application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Builder;

@Builder
public record EventoResponse(
        UUID id, String nome, List<InscricaoResponse> inscricoes, LocalDate data) {}
