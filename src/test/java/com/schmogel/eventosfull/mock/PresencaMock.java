package com.schmogel.eventosfull.mock;

import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.domain.model.Presenca;

import java.time.LocalDateTime;
import java.util.UUID;

public class PresencaMock {

  public static Presenca obterPresenca() {
    Inscricao inscricaoMock = InscricaoMock.obterInscricao();
    return new Presenca(UUID.randomUUID(), inscricaoMock, LocalDateTime.now());
  }

  public static Presenca obterPresenca(Inscricao inscricao) {
    return new Presenca(UUID.randomUUID(), inscricao, LocalDateTime.now());
  }
}
