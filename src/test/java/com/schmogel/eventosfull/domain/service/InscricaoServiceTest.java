package com.schmogel.eventosfull.domain.service;

import com.schmogel.eventosfull.application.dto.InscricaoRequest;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.infrastructure.repository.InscricaoRepository;
import com.schmogel.eventosfull.mock.InscricaoMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class InscricaoServiceTest {

  @Mock
  private InscricaoRepository inscricaoRepository;

  @Mock
  private UsuarioService usuarioService;

  @Mock
  private EventoService eventoService;

  @InjectMocks
  private InscricaoService inscricaoService;

  @Test
  void deveListarTodasAsInscricoes() {
    final var inscricaoMock1 = InscricaoMock.obterInscricao();
    final var inscricaoMock2 = InscricaoMock.obterInscricao();
    Mockito.when(inscricaoRepository.findAll()).thenReturn(List.of(inscricaoMock1, inscricaoMock2));

    final var result = inscricaoService.listarInscricoes();

    Assertions.assertNotNull(result);
    Assertions.assertFalse(result.isEmpty(), "A lista de inscrições não deve estar vazia");
    Assertions.assertEquals(2, result.size(), "A lista de inscrições deve conter 2 inscrições");
    Assertions.assertEquals(inscricaoMock1, result.get(0));
    Assertions.assertEquals(inscricaoMock2, result.get(1));
  }

  @Test
  void deveListarInscricoesPorUsuario() {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    final var usuarioId = UUID.randomUUID();
    Mockito.when(inscricaoRepository.findAllByUserInfoId(usuarioId)).thenReturn(List.of(inscricaoMock));

    final var result = inscricaoService.listarInscricoesPorUsuario(usuarioId);

    Assertions.assertNotNull(result);
    Assertions.assertFalse(result.isEmpty(), "A lista de inscrições não deve estar vazia");
    Assertions.assertEquals(1, result.size(), "A lista de inscrições deve conter 1 inscrição");
    Assertions.assertEquals(inscricaoMock, result.get(0));
  }

  @Test
  void deveRecuperarUmaInscricao() {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    Mockito.when(inscricaoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(inscricaoMock));

    final var result = inscricaoService.recuperarInscricao(UUID.randomUUID());

    Assertions.assertNotNull(result);
    Assertions.assertEquals(inscricaoMock, result);
  }

  @Test
  void deveRetornarNotFoundParaInscricao() {
    Mockito.when(inscricaoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());
    Assertions.assertThrows(NotFoundException.class, () -> inscricaoService.recuperarInscricao(UUID.randomUUID()));
  }

  @Test
  void deveCriarUmaNovaInscricao() {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    final var inscricaoRequest = new InscricaoRequest(inscricaoMock.getUserInfo().getId(), inscricaoMock.getEvento().getId());

    Mockito.when(usuarioService.recuperarUsuario(Mockito.any(UUID.class))).thenReturn(inscricaoMock.getUserInfo());
    Mockito.when(eventoService.recuperarEvento(Mockito.any(UUID.class))).thenReturn(inscricaoMock.getEvento());
    Mockito.when(inscricaoRepository.save(Mockito.any(Inscricao.class))).thenReturn(inscricaoMock);

    final var result = inscricaoService.criarInscricao(inscricaoRequest);

    Assertions.assertNotNull(result);
    Assertions.assertEquals(inscricaoMock.getId(), result.getId(), "O ID da inscrição criada está incorreto");
    Assertions.assertEquals(inscricaoRequest.usuarioId(), result.getUserInfo().getId(), "O ID do usuário da inscrição criada está incorreto");
    Assertions.assertEquals(inscricaoRequest.eventoId(), result.getEvento().getId(), "O ID do evento da inscrição criada está incorreto");
  }

  @Test
  void deveDeletarUmaInscricao() {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    Mockito.when(inscricaoRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(inscricaoMock));
    Mockito.doNothing().when(inscricaoRepository).delete(inscricaoMock);

    Assertions.assertDoesNotThrow(() -> inscricaoService.deletarInscricao(UUID.randomUUID()));
  }
}
