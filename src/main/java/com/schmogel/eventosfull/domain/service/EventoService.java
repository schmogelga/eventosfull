package com.schmogel.eventosfull.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.schmogel.eventosfull.application.dto.EventoRequest;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.infrastructure.repository.EventoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento recuperarEvento(UUID id) {
        return eventoRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Evento criarEvento(EventoRequest eventoRequest) {
        Evento evento =
                new Evento(null, eventoRequest.nome(), eventoRequest.data(), new ArrayList<Inscricao>(), false);

        return eventoRepository.save(evento);
    }
}
