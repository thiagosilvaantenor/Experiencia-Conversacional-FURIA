package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    private JogadorService jogadorService;

    @GetMapping
    public ResponseEntity<String> exibeMenu(){
        String menu = "Seja bem vindo(a) ao Chat do time de Counter Strike da Furia, escolha uma das opções:\n"
                + "1 - Jogadores que estão em live agora\n" + "2 - Redes sociais dos jogadores\n" + "3 - Mapas e skins favoritas dos jogadores\n" + "4 - Mandar uma suguestão para o chat";
        return  ResponseEntity.ok(menu);
    }


    @PostMapping
    public ResponseEntity<String> chat(@RequestBody String mensagem) {
        String response = gerarResposta(mensagem);
        return ResponseEntity.ok(response);
    }

    private String gerarResposta(String mensagem){

        mensagem = mensagem.trim();
        // TO FIX: Mensagem chega null mas não é acionado o menu
        if (mensagem.isEmpty() || mensagem == null) {
           exibeMenu();
        }
        List<Jogador> jogadores;
        StringBuilder builder;
        switch (mensagem) {
            case "1":
                return "Essa função ainda vai ser implementada";
            case "2":
                //TODO: REVER LÓGICA E RELACIONAMENTO ENTRE JOGADOR E REDES
                jogadores = jogadorService.buscarTodos();
                builder = new StringBuilder();

                jogadores.forEach( jogador -> {
                    builder.append(jogador.getNickName()).append(":\n");
                    builder.append("Twitch: ").append(jogador.getRedesSociais().getTwitch());
                    builder.append("\nYoutube: ").append(jogador.getRedesSociais().getYoutube());
                    builder.append("\nInstagram: ").append(jogador.getRedesSociais().getInstagram());
                    builder.append("\n-------------");
                    }
                );
                return builder.toString();

            case "3":
                jogadores = jogadorService.buscarTodos();
                builder = new StringBuilder();
                for (Jogador jogador : jogadores) {
                    builder.append(jogador.getNickName()).append(":\n");
                    builder.append(jogador.getMapaFavorito().getNome()).append("\n");
                    builder.append(jogador.getSkinFavorita().getNome()).append("\n");
                    builder.append(jogador.getSkinFavorita().getArma());
                    builder.append("\n-------------");
                }
                return builder.toString();
            case "4":
            //TODO: criar a suguestão aqui ou no /suguestoes?

            default:
                return "Opção invalida";
        }
    }

}
