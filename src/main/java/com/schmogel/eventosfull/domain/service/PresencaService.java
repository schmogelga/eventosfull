package com.schmogel.eventosfull.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.schmogel.eventosfull.application.dto.PresencaRequest;
import com.schmogel.eventosfull.domain.model.Presenca;
import com.schmogel.eventosfull.infrastructure.repository.PresencaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final InscricaoService inscricaoService;

    public Presenca criarPresenca(PresencaRequest presencaRequest) {
        Presenca presenca =
                new Presenca(
                        null,
                        inscricaoService.recuperarInscricao(presencaRequest.inscricaoId()),
                        LocalDateTime.now());

        return presencaRepository.save(presenca);
    }
}
