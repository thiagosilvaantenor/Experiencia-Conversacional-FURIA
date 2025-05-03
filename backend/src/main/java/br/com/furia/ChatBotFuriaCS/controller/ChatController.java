package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import br.com.furia.ChatBotFuriaCS.model.sugestao.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private SugestaoService sugestaoService;

    @GetMapping
    public ResponseEntity<HashMap<String,String>> exibeMenu(){
        HashMap<String,String> menu = new HashMap<>();
        menu.put("1", "Jogadores que estão em live agora");
        menu.put("2","Redes sociais dos jogadores");
        menu.put("3","Mapas e skins favoritas dos jogadores");
        menu.put("4","Mandar uma suguestão para o chat");
        return  ResponseEntity.ok(menu);
    }


    @PostMapping
    public ResponseEntity<HashMap<String,Object>> chat(@RequestBody Map<String, String> mensagem) throws Exception {
        String opcao = mensagem.get("opcao");
        HashMap<String,Object> response = gerarResposta(opcao, mensagem);
        return ResponseEntity.ok(response);
    }

    private HashMap<String,Object> gerarResposta(String opcao, Map<String,String> dados) throws Exception {
        HashMap<String, Object> resposta = new HashMap<>();

        switch (opcao) {
            case "1":
                List<Map<String, String>> jogadoresEmLive = buscaJogadoresEmLive();
                //Caso nenhum jogador estiver em Live, mostra o link dos canais de cada um
                if (jogadoresEmLive.isEmpty()){
                    resposta.put("mensagem", "Nenhum dos jogadores está em live agora.");
                    resposta.put("canais", buscaJogadoresERedesSociais());
                }else{
                    resposta.put("emLive", jogadoresEmLive);
                }
                return resposta;

            case "2":
                resposta.put("canais", buscaJogadoresERedesSociais());
                return resposta;

            case "3":
                resposta.put("skins&mapas",buscaJogadoresSkinEMapa());
                return resposta;
            case "4":
                criarSugestao(dados);
                resposta.put("mensagem","Sugestão cadastrada com sucesso");
                return resposta;
            default:
                resposta.put("mensagem", "Opção invalida");
                return resposta;
        }
    }

    private List<Map<String, String>> buscaJogadoresEmLive() throws Exception {
        TwitchAPIController api = new TwitchAPIController();
        List<Jogador> jogadores = jogadorService.buscarTodos();
        List<Map<String,String>> jogadoresEmLive = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            String[] twitch = jogador.getRedesSociais().getTwitch().split("/");
            String canal = twitch[twitch.length - 1];
            String json = api.buscaDados(canal);

            if (json != null) {
                try {
                    String resultado = api.verificarLiveEJogo(json);
                    if (resultado != null) {
                        //Cria um Map pra cada jogador que esteja em live
                        Map<String, String> dadosJogador = new HashMap<>();
                        dadosJogador.put("jogador", jogador.getNickName());
                        dadosJogador.put("jogando", resultado);
                        dadosJogador.put("canal", jogador.getRedesSociais().getTwitch());
                        jogadoresEmLive.add(dadosJogador);
                    }
                } catch (Exception e) {
                    throw new Exception("Erro ao processar resposta da Twitch");
                }
            }

        }
        return jogadoresEmLive;
    }

    private List<Map<String, String>> buscaJogadoresERedesSociais() {
        List<Jogador> jogadores = jogadorService.buscarTodos();
        List<Map<String, String>> canais = jogadores.stream().map(
                jogador -> {
                    Map<String, String> info = new HashMap<>();
                    info.put("jogador",jogador.getNickName());
                    info.put("twitch",jogador.getRedesSociais().getTwitch());
                    info.put("instagram",jogador.getRedesSociais().getInstagram());
                    info.put("youtube",jogador.getRedesSociais().getYoutube());
                    return info;
                }).collect(Collectors.toList());
        return canais;
    }

    private List<Map<String,String>> buscaJogadoresSkinEMapa() {

        List<Jogador> jogadores = jogadorService.buscarTodos();
        List<Map<String,String>> dados = jogadores.stream().map( jogador ->{
            Map<String,String> info = new HashMap<>();
            info.put("jogador", jogador.getNickName());
            info.put("mapaFavorito", jogador.getMapaFavorito().getNome());
            info.put("skinNome",jogador.getSkinFavorita().getNome());
            info.put("skinArma",jogador.getSkinFavorita().getArma());
            return info;
        }).collect(Collectors.toList());

        return dados;
    }

    private Sugestao criarSugestao(Map<String,String> dados){
        Sugestao sugestao = new Sugestao();
        sugestao.setTipo(dados.get("tipo"));
        sugestao.setDescricao(dados.get("descricao"));
        sugestao.setEmailUsuario(dados.get("emailUsuario"));

        Sugestao salva = sugestaoService.salvar(sugestao);

        return salva;
    }
}
