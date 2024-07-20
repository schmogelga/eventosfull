package com.schmogel.eventosfull.domain.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.schmogel.eventosfull.application.dto.UsuarioRequest;
import com.schmogel.eventosfull.application.exception.NotFoundException;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.domain.model.UserInfo;
import com.schmogel.eventosfull.domain.model.UserRole;
import com.schmogel.eventosfull.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UserInfo recuperarUsuario(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public UserInfo criarUsuario(UsuarioRequest usuarioRequest) {
        UserInfo userInfo =
                new UserInfo(
                        null,
                        "",
                        usuarioRequest.nome(),
                        usuarioRequest.email(),
                        new ArrayList<Inscricao>(),
                        new HashSet<UserRole>());

        return usuarioRepository.save(userInfo);
    }
}
