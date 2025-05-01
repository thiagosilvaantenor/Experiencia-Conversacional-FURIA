package br.com.furia.ChatBotFuriaCS.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class TwitchAPIController {
    private final String TOKEN = "nw5csmi2mljxy77spfc8m7buif5wso";
    private final String CLIENT_ID = "n4y6auo1jq9axiwfs8yw3ldeuvslis";


    public String buscaDados(String canal) {
        final String URL = "https://api.twitch.tv/helix/streams?user_login=" + canal;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Client-ID", CLIENT_ID);
        headers.set("Authorization", "Bearer " + TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
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

}
