package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.service.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import br.com.furia.ChatBotFuriaCS.service.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//RestController do chatbot em /chat
@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    //Anotação para dizer ao Spring que deverá instanciar essas Services e controller
    @Autowired
    private JogadorService jogadorService;

    @Autowired
    private SugestaoService sugestaoService;

    @Autowired
    private TwitchAPIController api;

    //Método get para exibir as opções de serviços do bot,
    // retorna HashMap<String,String> para facilitar a manipulação de dados do json no front end
    @GetMapping
    public ResponseEntity<HashMap<String,String>> exibeMenu(){
        HashMap<String,String> menu = new HashMap<>();
        menu.put("1", "Jogadores que estão em live agora");
        menu.put("2","Redes sociais dos jogadores");
        menu.put("3","Mapas e skins favoritas dos jogadores");
        menu.put("4","Mandar uma suguestão para o chat");
        //Retorna codigo de status ok/200 e no body o menu: {"valor":"descricão da opção"}
        return  ResponseEntity.ok(menu);
    }

    //Método post que requisita do body uma mensagem de chave e valor {"opcao" : "valor"}
    // onde o usuário vai mandar o valor da opção de serviço desejada
    @PostMapping
    public ResponseEntity<HashMap<String,Object>> chat(@RequestBody Map<String, String> mensagem) throws Exception {
        //pega da mensagem o valor da chave opcao, ex: opcao : 1, vai pegar o valor 1
        String opcao = mensagem.get("opcao");
        //Chama a função gerarResposta que vai lidar com as diferentes opções de serviço
        HashMap<String,Object> response = gerarResposta(opcao, mensagem);
        //envia o resultado para um HashMap que será a resposta da api, como código de status envia ok/200
        return ResponseEntity.ok(response);
    }
    //Método privado para gerarResposta do bot de acordo com a opção escolhida e, caso necessário os dados enviados
    private HashMap<String,Object> gerarResposta(String opcao, Map<String,String> dados) throws Exception {
        HashMap<String, Object> resposta = new HashMap<>();

        //Switch para lidar com os diferentes valores de opção, que represetam os serviços do bot
        //Separei o maximo possivel as responsabilidades dos serviços em métodos
        switch (opcao) {
            //Serviço de verificar se existem jogadores ao vivo
            case "1":
                //Chama o método responsavel pela busca de jogadores em live
                List<Map<String, String>> jogadoresEmLive = buscaJogadoresEmLive();
                //Caso nenhum jogador estiver em Live, mostra o link dos canais de cada um
                if (jogadoresEmLive.isEmpty()){
                    resposta.put("mensagem", "Nenhum dos jogadores está em live agora.");
                    resposta.put("canais", buscaJogadoresERedesSociais());
                }else{
                    //Caso tenha jogadores ao vivo envia a lista de jogadores com o nome da chave "emLive"
                    resposta.put("emLive", jogadoresEmLive);
                }
                //Envia o resultado
                return resposta;
            // ----------------------------------------------------- //
            //Servico de exibir todas as redesSociais dos jogadores cadastrados
            case "2":
                //Envia como chave 'canais' e como valor o resultado do método responsavel por esse serviço
                resposta.put("canais", buscaJogadoresERedesSociais());
                return resposta;
            // -------------------------------------------------------//
            //Serviço de exibir as skins e mapas favoritos de cada jogador
            case "3":
                //Envia como chave 'skins_mapas' e como valor o resultado do método responsavel
                resposta.put("skins_mapas",buscaJogadoresSkinEMapa());
                return resposta;
            // -------------------------------------------------------//
            //Serviço de criar sugestão
            case "4":
                //Chama o método responsavel
                Sugestao salva = criarSugestao(dados);
                if (salva != null){
                    //Envia como chave 'mensagem' e como valor a confirmação da sugestão salva
                    resposta.put("mensagem","Sugestão cadastrada com sucesso");
                } else{
                    resposta.put("mensagem", "Sugestão não foi salva, tente novamente verificando os dados antes de enviar");
                }
                return resposta;
            // -------------------------------------------------------//
            //Caso tenha escolhido um valor que não foi definido um serviço
            default:
                //Envia como chave 'mensagem' e o valor 'Opção invalida'
                resposta.put("mensagem", "Opção invalida");
                return resposta;
        }
    }

    //Método responsavel pelo serviço de valor 1, verificar se existem jogadores ao vivo
    private List<Map<String, String>> buscaJogadoresEmLive() throws Exception {
        //Busca todos os jogadores cadastrados
        List<Jogador> jogadores = jogadorService.buscarTodos();
        List<Map<String,String>> jogadoresEmLive = new ArrayList<>();
        for (Jogador jogador : jogadores) {
            //Itera por cada jogador da lista, pega o canal da twitch e separa do link o nome do canal
            String[] twitch = jogador.getRedesSociais().getTwitch().split("/");
            String canal = twitch[twitch.length - 1];
            //Com o nome do canal envia para o método de consumo da API da Twitch
            String json = api.buscaDados(canal);

            //Verifica se o json veio corretamente
            if (json != null) {
                //Se sim, tenta verificar se o jogador esta em live/ao vivo
                try {
                    String resultado = api.verificarLiveEJogo(json);
                    if (resultado != null) {
                        //Cria um Map pra cada jogador que esteja em live,
                        // com o nome dele, o jogo que esta jogando e o link do canal
                        Map<String, String> dadosJogador = new HashMap<>();
                        dadosJogador.put("jogador", jogador.getNickName());
                        dadosJogador.put("jogando", resultado);
                        dadosJogador.put("canal", jogador.getRedesSociais().getTwitch());
                        //Adiciona os dados desse jogador na lista que vai ser retornada
                        jogadoresEmLive.add(dadosJogador);
                    }
                } catch (Exception e) {
                    //Se o json esta nulo lança exceção
                    throw new Exception("Erro ao processar resposta da Twitch");
                }
            }

        }
        //Retorna lista com jogadores ao vivo
        return jogadoresEmLive;
    }
    //Método para o serviço de valor 2, exibir todas as redesSociais dos jogadores cadastrados
    private List<Map<String, String>> buscaJogadoresERedesSociais() {
        //Busca os jogadores cadastrados no banco de dados
        List<Jogador> jogadores = jogadorService.buscarTodos();
        //Itera sobre os jogadores
        // e cria um Map para cada:
        // {jogador: nickName, nome da rede: link da rede}
        List<Map<String, String>> canais = jogadores.stream().map(
                jogador -> {
                    Map<String, String> info = new HashMap<>();
                    info.put("jogador",jogador.getNickName());
                    info.put("twitch",jogador.getRedesSociais().getTwitch());
                    info.put("instagram",jogador.getRedesSociais().getInstagram());
                    info.put("youtube",jogador.getRedesSociais().getYoutube());
                    return info;

                })//Pega tudo que foi retornado dentro do map e transforma em lista
                .collect(Collectors.toList());
        //Retorna a lista de Maps
        return canais;
    }

    //Método para o serviço de valor 3: exibir as skins e mapas favoritos de cada jogador
    private List<Map<String,String>> buscaJogadoresSkinEMapa() {
        //Busca todos os jogadores cadastrados no banco de dados
        List<Jogador> jogadores = jogadorService.buscarTodos();
        //Itera sobre os jogadores,
        // e cria um Map para cada:
        // { jogador : nomeJogador, mapaFavorito : nome do Mapa, skinNome : nome da skin, skinArma : arma da skin}
        List<Map<String,String>> dados = jogadores.stream().map( jogador ->{
            Map<String,String> info = new HashMap<>();
            info.put("jogador", jogador.getNickName());
            info.put("mapaFavorito", jogador.getMapaFavorito().getNome());
            info.put("skinNome",jogador.getSkinFavorita().getNome());
            info.put("skinArma",jogador.getSkinFavorita().getArma());
            return info;
        })//Pega tudo que foi retornado dentro do map e transforma em lista
        .collect(Collectors.toList());
        //Retorna a lista de Maps
        return dados;
    }
    //Método para o servico de valor 4: criar sugestão
    private Sugestao criarSugestao(Map<String,String> dados){
        //Cria a sugestão com os dados do Map, buscando os valores pelas chaves
        Sugestao sugestao = new Sugestao();
        sugestao.setTipo(dados.get("tipo"));
        sugestao.setDescricao(dados.get("descricao"));
        sugestao.setEmailUsuario(dados.get("emailUsuario"));
        //Salva a sugestão no banco de dados
        Sugestao salva = sugestaoService.salvar(sugestao);
        //Se foi salva retorna a sugestão
        if (salva != null){
            return salva;
        }//Se não retorna null
        return null;
    }
}
