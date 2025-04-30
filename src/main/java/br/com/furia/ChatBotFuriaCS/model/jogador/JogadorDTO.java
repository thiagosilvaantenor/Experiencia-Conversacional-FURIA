package br.com.furia.ChatBotFuriaCS.model.jogador;

public record JogadorDTO(
        String nickName,
        String nome,
        int idade,
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