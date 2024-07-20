package com.schmogel.eventosfull.application.dto;

import java.time.LocalDate;

public record EventoRequest(String nome, LocalDate data) {}
