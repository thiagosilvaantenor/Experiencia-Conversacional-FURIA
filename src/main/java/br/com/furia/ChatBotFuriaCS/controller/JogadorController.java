package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    @Autowired
    private JogadorService service;

    @PostMapping
    public ResponseEntity<Jogador> criarJogador(@RequestBody Jogador jogador) {
        Jogador novo = service.salvar(jogador);
        if (novo != null) {
            return ResponseEntity.status(201).body(novo);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> listarTodos() {
        List<Jogador> jogadores = service.buscarTodos();
        return ResponseEntity.ok(jogadores);
    }
    @GetMapping("/{nickName}")
    public ResponseEntity<Jogador> exibirJogador(@PathVariable String nickName) {
        Optional<Jogador> jogador = service.buscarPeloId(nickName);
        if (jogador.isPresent()){
            return ResponseEntity.ok(jogador.get());
        }
        return ResponseEntity.notFound().build();
    }
}
