package br.com.furia.ChatBotFuriaCS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

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

        switch (mensagem) {
            case "1":
                return "Oi! Como posso te ajudar hoje?";
            case "2":
                return "Tchau Volte Sempre";
            case "3":
                return "Claro! Em que posso ajudar?";
            default:
                return "Opção invalida";
        }
    }

}
