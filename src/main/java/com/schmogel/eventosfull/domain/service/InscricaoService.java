package com.schmogel.eventosfull.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.schmogel.eventosfull.application.dto.InscricaoRequest;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.infrastructure.repository.InscricaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final UsuarioService usuarioService;
    private final EventoService eventoService;

    public List<Inscricao> listarInscricoes() {
        return inscricaoRepository.findAll();
    }

    public List<Inscricao> listarInscricoesPorUsuario(UUID usuarioId) {
        return inscricaoRepository.findAllByUserInfoId(usuarioId);
    }

    public Inscricao recuperarInscricao(UUID idInscricao) {
        return inscricaoRepository.findById(idInscricao).orElseThrow(NotFoundException::new);
    }

    public Inscricao criarInscricao(InscricaoRequest inscricaoRequest) {
        Inscricao inscricao =
                new Inscricao(
                        null,
                        usuarioService.recuperarUsuario(inscricaoRequest.usuarioId()),
                        eventoService.recuperarEvento(inscricaoRequest.eventoId()));

        return inscricaoRepository.save(inscricao);
    }

    public void deletarInscricao(UUID idInscricao) {
        inscricaoRepository.delete(recuperarInscricao(idInscricao));
    }
}
