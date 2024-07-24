package com.schmogel.eventosfull.application.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schmogel.eventosfull.application.dto.EventoRequest;
import com.schmogel.eventosfull.application.dto.EventoResponse;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.application.web.evento.EventoController;
import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.domain.service.EventoService;
import com.schmogel.eventosfull.mock.EventoMock;
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

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebMvcTest(EventoController.class)
class EventoControllerTest {

  @MockBean
  private EventoService eventoService;

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setup() {

  }

  @Test
  void deveListarEventos() throws Exception {
    final var eventoMock = EventoMock.obterEvento();
    Mockito.when(eventoService.listarEventos()).thenReturn(List.of(eventoMock));

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/eventos").contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();


    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    List<Evento> eventos = objectMapper.readValue(result, new TypeReference<List<Evento>>() {});
    Assertions.assertFalse(eventos.isEmpty(), "A lista de eventos não deve estar vazia");
    Assertions.assertEquals(eventoMock.getId(), eventos.get(0).getId(), "O ID do evento retornado está incorreto");
  }

  @Test
  void deveRecuperarEvento() throws Exception {
    final var eventoMock = EventoMock.obterEvento();
    Mockito.when(eventoService.recuperarEvento(eventoMock.getId())).thenReturn(eventoMock);

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/eventos/" + eventoMock.getId())
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    Evento evento = objectMapper.readValue(result, Evento.class);
    Assertions.assertNotNull(evento, "O evento recuperado não deve ser nulo");
    Assertions.assertEquals(eventoMock.getId(), evento.getId(), "O ID do evento retornado está incorreto");
  }

  @Test
  void deveCriarEvento() throws Exception {
    final var eventoMock = EventoMock.obterEvento();
    EventoRequest eventoRequest = new EventoRequest("Nome Evento", LocalDate.now());
    Mockito.when(eventoService.criarEvento(eventoRequest)).thenReturn(eventoMock);

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    String requestBody = objectMapper.writeValueAsString(eventoRequest);

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.post("/eventos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();

    EventoResponse response = objectMapper.readValue(result, EventoResponse.class);
    Assertions.assertNotNull(response, "A resposta do evento criado não deve ser nula");
    Assertions.assertEquals(eventoMock.getId(), response.id(), "O ID do evento retornado está incorreto");
  }

  @Test
  void deveRetornarNotFoundParaEventoNaoEncontrado() throws Exception {
    UUID id = UUID.randomUUID();
    Mockito.when(eventoService.recuperarEvento(id)).thenThrow(new NotFoundException("Evento não encontrado"));

    final var result = mockMvc
      .perform(MockMvcRequestBuilders.get("/eventos/" + id)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isNotFound())
      .andReturn()
      .getResponse()
      .getContentAsString();

    Assertions.assertTrue(result.contains("Evento não encontrado"), "A mensagem de erro não está correta");
  }
}
