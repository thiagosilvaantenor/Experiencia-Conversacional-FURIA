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

    private String clientId = "";

    private String clientSecret = "";

    private String token = "";
    private Instant expiraEm;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public TwitchAPIController(
            @Value("${twitch.client-id}") String clientId,
            @Value("${twitch.client-secret}") String clientSecret
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }


    public String buscaDados(String canal) throws Exception {

        if (this.token == null || this.expiraEm == null || Instant.now().isAfter(this.expiraEm)) {
            renovarToken();
        }

        final String URL = "https://api.twitch.tv/helix/streams?user_login=" + canal;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", clientId);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }

    public String verificarLiveEJogo(String json) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode dados = root.path("data");

            if(dados.isArray() && !dados.isEmpty()) {
                JsonNode stream = dados.get(0);
                return stream.path("game_name").asText();
            } else{
                return null;
            }
    }

     private void renovarToken() throws Exception {
        String url = "https://id.twitch.tv/oauth2/token"
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&grant_type=client_credentials";

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonNode json = mapper.readTree(response.getBody());
            this.token = json.path("access_token").asText();
            int expiresIn = json.path("expires_in").asInt();
            // margem de segurança de 1 min
            this.expiraEm = Instant.now().plusSeconds(expiresIn - 60);
        } else {
            throw new RuntimeException("Erro ao obter token da Twitch: " + response.getStatusCode());
        }
    }

}
