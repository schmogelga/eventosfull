package com.schmogel.eventosfull.application.web.inscricao;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.schmogel.eventosfull.application.adapter.InscricaoAdapter;
import com.schmogel.eventosfull.application.adapter.PresencaAdapter;
import com.schmogel.eventosfull.application.dto.InscricaoRequest;
import com.schmogel.eventosfull.application.dto.InscricaoResponse;
import com.schmogel.eventosfull.application.dto.PresencaRequest;
import com.schmogel.eventosfull.application.dto.PresencaResponse;
import com.schmogel.eventosfull.domain.service.InscricaoService;
import com.schmogel.eventosfull.domain.service.PresencaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InscricaoController implements InscricaoApi {

    private final InscricaoService inscricaoService;
    private final PresencaService presencaService;

    @Override
    public List<InscricaoResponse> listarInscricoesPorUsuario(UUID usuarioId) {
        return Objects.isNull(usuarioId)
                ? inscricaoService.listarInscricoes().stream().map(InscricaoAdapter::entityToDTO).toList()
                : inscricaoService.listarInscricoesPorUsuario(usuarioId).stream()
                        .map(InscricaoAdapter::entityToDTO)
                        .toList();
    }

    @Override
    public InscricaoResponse recuperarInscricao(UUID inscricaoId) {
        return InscricaoAdapter.entityToDTO(inscricaoService.recuperarInscricao(inscricaoId));
    }

    @Override
    public InscricaoResponse criarInscricao(InscricaoRequest inscricaoRequest) {
        return InscricaoAdapter.entityToDTO(inscricaoService.criarInscricao(inscricaoRequest));
    }

    @Override
    public void deletarInscricao(UUID inscricaoId) {
        inscricaoService.deletarInscricao(inscricaoId);
    }

    @Override
    public PresencaResponse criarPresenca(@RequestBody PresencaRequest presencaRequest) {
        return PresencaAdapter.entityToDto(presencaService.criarPresenca(presencaRequest));
    }
}
