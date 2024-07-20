package com.schmogel.eventosfull.application.web.evento;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RestController;

import com.schmogel.eventosfull.application.adapter.EventoAdapter;
import com.schmogel.eventosfull.application.dto.EventoRequest;
import com.schmogel.eventosfull.application.dto.EventoResponse;
import com.schmogel.eventosfull.domain.service.EventoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EventoController implements EventoApi {

    private final EventoService eventoService;

    @Override
    public List<EventoResponse> listarEventos() {
        return eventoService.listarEventos().stream().map(EventoAdapter::entityToDto).toList();
    }

    @Override
    public EventoResponse recuperarEvento(UUID id) {
        return EventoAdapter.entityToDto(eventoService.recuperarEvento(id));
    }

    @Override
    public EventoResponse criarEvento(EventoRequest eventoRequest) {
        return EventoAdapter.entityToDto(eventoService.criarEvento(eventoRequest));
    }
}
