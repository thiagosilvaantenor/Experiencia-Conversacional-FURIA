package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import br.com.furia.ChatBotFuriaCS.model.sugestao.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private SugestaoService sugestaoService;

    @GetMapping
    public ResponseEntity<String> exibeMenu(){
        String menu = "Seja bem vindo(a) ao Chat do time de Counter Strike da Furia, escolha uma das opções:\n"
                + "1 - Jogadores que estão em live agora\n" + "2 - Redes sociais dos jogadores\n" + "3 - Mapas e skins favoritas dos jogadores\n" + "4 - Mandar uma suguestão para o chat";
        return  ResponseEntity.ok(menu);
    }


    @PostMapping
    public ResponseEntity<String> chat(@RequestBody Map<String, String> mensagem) throws Exception {
        String opcao = mensagem.get("opcao");
        String response = gerarResposta(opcao, mensagem);
        return ResponseEntity.ok(response);
    }

    private String gerarResposta(String opcao, Map<String,String> dados) throws Exception {

        opcao = opcao.trim();
        // TO FIX: Mensagem chega null mas não é acionado o menu
        if (opcao.isEmpty()) {
           exibeMenu();
        }
        List<Jogador> jogadores;
        StringBuilder builder;
        switch (opcao) {
            case "1":
                TwitchAPIController api = new TwitchAPIController();
                builder = new StringBuilder();
                jogadores = jogadorService.buscarTodos();
                for (Jogador jogadore : jogadores) {
                    String[] twitch = jogadore.getRedesSociais().getTwitch().split("/");
                    String canal = twitch[twitch.length - 1];
                    String json = api.buscaDados(canal);

                    if (json != null) {
                        try {
                            String resultado = api.verificarLiveEJogo(json);
                            if (resultado != null) {
                                builder.append("Jogador: ").append(jogadore.getNickName()).append(" está AO VIVO\n");
                                builder.append("Jogando: ").append(resultado).append("\n");
                                builder.append(jogadore.getRedesSociais().getTwitch()).append("\n");
                            }
                        } catch (Exception e) {
                            throw new Exception("Erro ao processar resposta da Twitch");
                        }
                    }
                }
                //Caso nenhum jogador estiver em Live, mostra o link dos canais de cada um
                if (builder.isEmpty()){
                    builder.append("Nenhum dos jogodores está em live agora\n");
                    jogadores.forEach(jogador -> {
                        builder.append(jogador.getNickName())
                                .append(" canal: ").append(jogador.getRedesSociais().getTwitch())
                                .append("\n");
                    } );
                }
                return  builder.toString();

            case "2":
                jogadores = jogadorService.buscarTodos();
                builder = new StringBuilder();

                jogadores.forEach( jogador -> {
                    builder.append(jogador.getNickName()).append(":\n");
                    builder.append("Twitch: ").append(jogador.getRedesSociais().getTwitch());
                    builder.append("\nYoutube: ").append(jogador.getRedesSociais().getYoutube());
                    builder.append("\nInstagram: ").append(jogador.getRedesSociais().getInstagram());
                    builder.append("\n-------------\n");
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
                    builder.append("\n-------------\n");
                }
                return builder.toString();
            case "4":
                Sugestao sugestao = new Sugestao();
                sugestao.setTipo(dados.get("tipo"));
                sugestao.setDescricao(dados.get("descricao"));
                sugestao.setEmailUsuario(dados.get("emailUsuario"));

                sugestaoService.salvar(sugestao);
                return "Sugestão recebida com sucesso! Obrigado pela sua contribuição";
            default:
                return "Opção invalida";
        }
    }

}
