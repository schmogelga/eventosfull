package com.schmogel.eventosfull.domain.service;

import com.schmogel.eventosfull.application.dto.EventoRequest;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.infrastructure.repository.EventoRepository;
import com.schmogel.eventosfull.mock.EventoMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

  @Mock private EventoRepository eventoRepository;

  @InjectMocks private EventoService eventoService;

  @Test
  void deveRecuperarUmEvento(){
    final var eventoMock = EventoMock.obterEvento();
    Mockito.when(eventoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(eventoMock));

    final var result = eventoService.recuperarEvento(UUID.randomUUID());

    Assertions.assertNotNull(result);
    Assertions.assertEquals(eventoMock, result);

  }

  @Test
  void deveRetornarNotFound(){
    Mockito.when(eventoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> eventoService.recuperarEvento(UUID.randomUUID()));
  }

  @Test
  void deveListarTodosOsEventos() {
    final var eventoMock1 = EventoMock.obterEvento();
    final var eventoMock2 = EventoMock.obterEvento();
    Mockito.when(eventoRepository.findAll()).thenReturn(List.of(eventoMock1, eventoMock2));

    final var result = eventoService.listarEventos();

    Assertions.assertNotNull(result);
    Assertions.assertFalse(result.isEmpty(), "A lista de eventos não deve estar vazia");
    Assertions.assertEquals(2, result.size(), "A lista de eventos deve conter 2 eventos");
    Assertions.assertEquals(eventoMock1, result.get(0));
    Assertions.assertEquals(eventoMock2, result.get(1));
  }

  @Test
  void deveCriarUmNovoEvento() {
    final var eventoMock = EventoMock.obterEvento();
    final var eventoRequest = new EventoRequest(eventoMock.getNome(), eventoMock.getData());

    Mockito.when(eventoRepository.save(Mockito.any(Evento.class))).thenReturn(eventoMock);

    final var result = eventoService.criarEvento(eventoRequest);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(eventoMock.getId(), result.getId(), "O ID do evento criado está incorreto");
    Assertions.assertEquals(eventoRequest.nome(), result.getNome(), "O nome do evento criado está incorreto");
    Assertions.assertEquals(eventoRequest.data(), result.getData(), "A data do evento criado está incorreta");
  }
}
