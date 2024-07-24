package com.schmogel.eventosfull.mock;

import com.schmogel.eventosfull.domain.model.Evento;

import java.time.LocalDate;
import java.util.UUID;

public class EventoMock {

  public static Evento obterEvento(){
    return new Evento(UUID.randomUUID(), "", LocalDate.now(), null, false);
  }
}
