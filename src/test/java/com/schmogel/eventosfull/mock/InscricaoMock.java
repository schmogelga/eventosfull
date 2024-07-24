package com.schmogel.eventosfull.mock;

import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.domain.model.UserInfo;

import java.util.UUID;

public class InscricaoMock {


  public static Inscricao obterInscricao() {

    Evento eventoMock = EventoMock.obterEvento();
    return new Inscricao(UUID.randomUUID(), UserMock.getUserMock(), eventoMock);
  }

}
