package com.schmogel.eventosfull.application.web.inscricao;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.schmogel.eventosfull.application.dto.InscricaoRequest;
import com.schmogel.eventosfull.application.dto.InscricaoResponse;
import com.schmogel.eventosfull.application.dto.PresencaRequest;
import com.schmogel.eventosfull.application.dto.PresencaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Inscrições", description = "Operações de inscriçoes")
@RequestMapping(value = "/inscricoes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface InscricaoApi {

    @Operation(summary = "Lista todos as inscrições", description = "Lista todos as inscrições")
    @GetMapping
    List<InscricaoResponse> listarInscricoesPorUsuario(
            @RequestParam(required = false) UUID usuarioId);

    @Operation(summary = "Recupera uma inscrição", description = "Recupera um inscrição")
    @GetMapping("/{inscricaoId}")
    InscricaoResponse recuperarInscricao(@PathVariable UUID inscricaoId);

    @Operation(summary = "Cria uma inscricao", description = "Cria uma inscricao", method = "POST")
    @PostMapping
    InscricaoResponse criarInscricao(@RequestBody InscricaoRequest inscricaoRequest);

    @Operation(
            summary = "Deleta uma inscricao",
            description = "Deleta uma inscricao",
            method = "DELETE")
    @DeleteMapping("/{inscricaoId}")
    void deletarInscricao(@PathVariable UUID inscricaoId);

    @Operation(summary = "Cria uma presenca", description = "Cria uma presenca", method = "POST")
    @PostMapping("/presenca")
    PresencaResponse criarPresenca(@RequestBody PresencaRequest presencaRequest);
}
