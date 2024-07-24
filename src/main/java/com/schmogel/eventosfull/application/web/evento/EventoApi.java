package com.schmogel.eventosfull.application.web.evento;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.schmogel.eventosfull.application.dto.EventoRequest;
import com.schmogel.eventosfull.application.dto.EventoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Eventos", description = "Operações de eventos")
@RequestMapping(value = "/eventos", produces = MediaType.APPLICATION_JSON_VALUE)
public interface EventoApi {

    @Operation(summary = "Lista todos os eventos", description = "Lista todos os eventos")
    @GetMapping
    List<EventoResponse> listarEventos();

    @Operation(summary = "Recupera um evento", description = "Recupera um evento")
    @GetMapping("/{id}")
    EventoResponse recuperarEvento(@PathVariable UUID id);

    @Operation(summary = "Cria um evento", description = "Cria um evento", method = "POST")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    EventoResponse criarEvento(@RequestBody EventoRequest eventoRequest);
}
