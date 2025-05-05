package br.com.furia.ChatBotFuriaCS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitchAPIController {
    //Chaves da API
    private String clientId = "";
    private String clientSecret = "";

    //Token necessário para requisições, tem validade por isso ao gerar será registrado o tempo para expriar
    private String token = "";
    private Instant expiraEm;
    //RestTemplate para realizar requisição para renovar o token
    private final RestTemplate restTemplate = new RestTemplate();
    //
    private final ObjectMapper mapper = new ObjectMapper();

    //Construtor da classe para preencher as chaves da api com o valor que deve estar em application.properties
    public TwitchAPIController(
            @Value("${twitch.client-id}") String clientId,
            @Value("${twitch.client-secret}") String clientSecret
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    //Método de consumo da API da Twitch, recebe o canal da twitch do jogador, retorna o json em String
    public String buscaDados(String canal) throws Exception {
        //Verifica se o token está vazio ou o tempo de expiração do token ou se o token já expirou
        if (this.token == null || this.expiraEm == null || Instant.now().isAfter(this.expiraEm)) {
            //Se sim chama a Método de renovação do token
            renovarToken();
        }
        //Cria a URL com o valor do canal da twitch
        final String URL = "https://api.twitch.tv/helix/streams?user_login=" + canal;
        //Cria a requisição passando nos headers a chave e o token
        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + token);

        //Cria a entidade http para realizar a requisição
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //Realiza a requsisição para a API da Twitch e salva a resposta
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        //Retorna o body da resposta
        return response.getBody();
    }
    //Método para verificar se algum jogador está ao vivo na twitch, se tiver retorna o nome do jogo
    public String verificarLiveEJogo(String json) throws JsonProcessingException {
            //Usa o mapper para ler e navegar nos dados do json
            JsonNode root = mapper.readTree(json);
            JsonNode dados = root.path("data");

            //Se receber um array e não estiver vazio, deu tudo certo
            if(dados.isArray() && !dados.isEmpty()) {
                //pega o primeiro item do array e navega por ele e retorna o nome do jogo
                JsonNode stream = dados.get(0);
                return stream.path("game_name").asText();
            } else{
                //Se não é array ou estiver vazio deu errado, retorna nulo
                return null;
            }
    }
    //Método para renovarToken necessário para requisições com a API
     private void renovarToken() throws Exception {
        //Cria a url da requisiçao para obter o token, colocando as chaves da API
        String url = "https://id.twitch.tv/oauth2/token"
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";
        // Cria a requisição POST para obter a resposta com o token
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        //Verifica o código de status da requisição, se for 200/OK deu tudo certo
        if (response.getStatusCode() == HttpStatus.OK) {
            //Navega pelo body para obter o token e o tempo de expiração
            JsonNode json = mapper.readTree(response.getBody());
            this.token = json.path("access_token").asText();
            int expiresIn = json.path("expires_in").asInt();
            // margem de segurança de 1 min
            this.expiraEm = Instant.now().plusSeconds(expiresIn - 60);
        } else {
            //Caso o código for diferente de 200/OK a requisição deu erro, lança exceção com o código de status da requisição
            throw new RuntimeException("Erro ao obter token da Twitch: " + response.getStatusCode());
        }
    }

}
