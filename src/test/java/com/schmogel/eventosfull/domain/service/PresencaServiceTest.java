package com.schmogel.eventosfull.domain.service;

import com.schmogel.eventosfull.application.dto.PresencaRequest;
import com.schmogel.eventosfull.domain.model.Presenca;
import com.schmogel.eventosfull.infrastructure.repository.PresencaRepository;
import com.schmogel.eventosfull.mock.InscricaoMock;
import com.schmogel.eventosfull.mock.PresencaMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PresencaServiceTest {

  @Mock
  private PresencaRepository presencaRepository;

  @Mock
  private InscricaoService inscricaoService;

  @InjectMocks
  private PresencaService presencaService;

  @Test
  void deveCriarUmaNovaPresenca() {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    final var presencaMock = PresencaMock.obterPresenca(inscricaoMock);
    final var presencaRequest = new PresencaRequest(inscricaoMock.getId());

    Mockito.when(inscricaoService.recuperarInscricao(presencaRequest.inscricaoId())).thenReturn(inscricaoMock);
    Mockito.when(presencaRepository.save(Mockito.any(Presenca.class))).thenReturn(presencaMock);

    final var result = presencaService.criarPresenca(presencaRequest);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(presencaMock.getId(), result.getId(), "O ID da presença criada está incorreto");
    Assertions.assertEquals(presencaMock.getInscricao().getId(), result.getInscricao().getId(), "O ID da inscrição na presença criada está incorreto");
    Assertions.assertEquals(presencaMock.getDataHoraCheckin(), result.getDataHoraCheckin(), "A data e hora do check-in estão incorretas");
  }
}
