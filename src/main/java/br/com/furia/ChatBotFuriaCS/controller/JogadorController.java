package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorDTO;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
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
    public ResponseEntity<Jogador> criarJogador(@RequestBody JogadorDTO jogador) {
        //Criar RedesSociais
        RedesSociais redes = new RedesSociais();
        redes.setTwitch(jogador.twitch());
        redes.setYoutube(jogador.youtube());
        redes.setInstagram(jogador.instagram());

        //Criar Mapa Favorito
        MapaFavorito mapa = new MapaFavorito();
        mapa.setNome(jogador.nomeMapa());
        //Criar Skin Favorita:
        SkinFavorita skin = new SkinFavorita();
        skin.setNome(jogador.nomeSkin());
        skin.setArma(jogador.arma());

        Jogador novo = new Jogador(jogador.nickName(), jogador.nome(), jogador.idade(), redes, mapa, skin);

        redes.setJogador(novo);
        skin.getJogadores().add(novo);
        mapa.getJogadores().add(novo);

        //salva o jogador
        Jogador salvo = service.salvar(novo);

        if (salvo != null) {
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
