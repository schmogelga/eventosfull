package com.schmogel.eventosfull.domain.service;

import com.schmogel.eventosfull.infrastructure.repository.EventoRepository;
import com.schmogel.eventosfull.mock.EventoMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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
}
