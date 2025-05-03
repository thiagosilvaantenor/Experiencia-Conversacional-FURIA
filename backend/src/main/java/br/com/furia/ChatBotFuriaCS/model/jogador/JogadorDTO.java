package br.com.furia.ChatBotFuriaCS.model.jogador;

import java.time.LocalDate;

public record JogadorDTO(
        String nickName,
        String nome,
        LocalDate nascimento,
        // Redes sociais
        String twitch,
        String instagram,
        String youtube,
        // Mapa
        String nomeMapa,
        //Skin
        String nomeSkin,
        String arma)
{}