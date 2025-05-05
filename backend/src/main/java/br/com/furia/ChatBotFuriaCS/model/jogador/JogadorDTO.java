package br.com.furia.ChatBotFuriaCS.model.jogador;

import java.time.LocalDate;
//DTO para cadastro e atualização de Jogador, feito com record devido não haver necessidade de setters
public record JogadorDTO(
        //Jogador
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