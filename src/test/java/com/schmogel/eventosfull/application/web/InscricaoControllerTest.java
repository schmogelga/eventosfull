package com.schmogel.eventosfull.application.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schmogel.eventosfull.application.dto.InscricaoRequest;
import com.schmogel.eventosfull.application.dto.InscricaoResponse;
import com.schmogel.eventosfull.application.dto.PresencaRequest;
import com.schmogel.eventosfull.application.dto.PresencaResponse;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.application.web.inscricao.InscricaoController;
import com.schmogel.eventosfull.domain.service.InscricaoService;
import com.schmogel.eventosfull.domain.service.PresencaService;
import com.schmogel.eventosfull.mock.InscricaoMock;
import com.schmogel.eventosfull.mock.PresencaMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@WebMvcTest(InscricaoController.class)
class InscricaoControllerTest {

  @MockBean
  private InscricaoService inscricaoService;

  @MockBean
  private PresencaService presencaService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {
  }

  @Test
  void deveListarInscricoes() throws Exception {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    Mockito.when(inscricaoService.listarInscricoes()).thenReturn(List.of(inscricaoMock));

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/inscricoes")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    List<InscricaoResponse> inscricoes = objectMapper.readValue(result, new TypeReference<List<InscricaoResponse>>() {});
    Assertions.assertFalse(inscricoes.isEmpty(), "A lista de inscrições não deve estar vazia");
    Assertions.assertEquals(inscricaoMock.getId(), inscricoes.get(0).id(), "O ID da inscrição retornada está incorreto");
  }

  @Test
  void deveRecuperarInscricao() throws Exception {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    Mockito.when(inscricaoService.recuperarInscricao(inscricaoMock.getId())).thenReturn(inscricaoMock);

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/inscricoes/" + inscricaoMock.getId())
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    InscricaoResponse inscricao = objectMapper.readValue(result, InscricaoResponse.class);
    Assertions.assertNotNull(inscricao, "A inscrição recuperada não deve ser nula");
    Assertions.assertEquals(inscricaoMock.getId(), inscricao.id(), "O ID da inscrição retornada está incorreto");
  }

  @Test
  void deveCriarInscricao() throws Exception {
    final var inscricaoMock = InscricaoMock.obterInscricao();
    InscricaoRequest inscricaoRequest = new InscricaoRequest(UUID.randomUUID(), UUID.randomUUID());
    Mockito.when(inscricaoService.criarInscricao(inscricaoRequest)).thenReturn(inscricaoMock);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    String requestBody = objectMapper.writeValueAsString(inscricaoRequest);

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.post("/inscricoes")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();

    InscricaoResponse response = objectMapper.readValue(result, InscricaoResponse.class);
    Assertions.assertNotNull(response, "A resposta da inscrição criada não deve ser nula");
    Assertions.assertEquals(inscricaoMock.getId(), response.id(), "O ID da inscrição retornada está incorreto");
  }

  @Test
  void deveDeletarInscricao() throws Exception {
    UUID inscricaoId = UUID.randomUUID();
    Mockito.doNothing().when(inscricaoService).deletarInscricao(inscricaoId);

    mockMvc
      .perform(MockMvcRequestBuilders.delete("/inscricoes/" + inscricaoId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  void deveCriarPresenca() throws Exception {
    final var presencaMock = PresencaMock.obterPresenca();
    PresencaRequest presencaRequest = new PresencaRequest(UUID.randomUUID());
    Mockito.when(presencaService.criarPresenca(presencaRequest)).thenReturn(presencaMock);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    String requestBody = objectMapper.writeValueAsString(presencaRequest);

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.post("/inscricoes/presenca")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();

    PresencaResponse response = objectMapper.readValue(result, PresencaResponse.class);
    Assertions.assertNotNull(response, "A resposta da presença criada não deve ser nula");
    Assertions.assertEquals(presencaMock.getId(), response.id(), "O ID da presença retornada está incorreto");
  }

  @Test
  void deveRetornarNotFoundParaInscricaoNaoEncontrada() throws Exception {
    UUID inscricaoId = UUID.randomUUID();
    Mockito.when(inscricaoService.recuperarInscricao(inscricaoId)).thenThrow(new NotFoundException("Inscrição não encontrada"));

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/inscricoes/" + inscricaoId)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNotFound())
      .andReturn()
      .getResponse()
      .getContentAsString();

    Assertions.assertTrue(result.contains("Inscrição não encontrada"), "A mensagem de erro não está correta");
  }
}
