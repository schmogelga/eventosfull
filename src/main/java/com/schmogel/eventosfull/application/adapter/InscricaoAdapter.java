package com.schmogel.eventosfull.application.adapter;

import java.util.Optional;

import com.schmogel.eventosfull.application.dto.InscricaoResponse;
import com.schmogel.eventosfull.domain.model.Evento;
import com.schmogel.eventosfull.domain.model.Inscricao;
import com.schmogel.eventosfull.domain.model.UserInfo;

public class InscricaoAdapter {

    public static InscricaoResponse entityToDTO(Inscricao inscricao) {
        return InscricaoResponse.builder()
                .id(inscricao.getId())
                .usuarioId(Optional.ofNullable(inscricao.getUserInfo()).map(UserInfo::getId).orElse(null))
                .eventoId(Optional.ofNullable(inscricao.getEvento()).map(Evento::getId).orElse(null))
                .build();
    }
}
