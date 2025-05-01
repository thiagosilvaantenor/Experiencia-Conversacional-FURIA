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

import java.util.ArrayList;
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

        Jogador novo = new Jogador(jogador.nickName(), jogador.nome(), jogador.nascimento(), redes, mapa, skin);

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
    public ResponseEntity<List<JogadorDTO>> listarTodos() {
        List<Jogador> jogadores = service.buscarTodos();
        List<JogadorDTO> jogadorDTOS = new ArrayList<>();
        jogadores.forEach( jogador -> {
                 jogadorDTOS.add(new JogadorDTO(jogador.getNickName(), jogador.getNome(), jogador.getNascimento(),
                         jogador.getRedesSociais().getTwitch(), jogador.getRedesSociais().getInstagram(), jogador.getRedesSociais().getYoutube(),
                         jogador.getMapaFavorito().getNome(),
                         jogador.getSkinFavorita().getNome(), jogador.getSkinFavorita().getArma())
                 );
                });
        return ResponseEntity.ok(jogadorDTOS);
    }
    @GetMapping("/{nickName}")
    public ResponseEntity<JogadorDTO> exibirJogador(@PathVariable String nickName) {
        Optional<Jogador> jogador = service.buscarPeloId(nickName);
        if (jogador.isPresent()){
            Jogador buffer = jogador.get();
            return ResponseEntity.ok(new JogadorDTO(buffer.getNickName(), buffer.getNome(), buffer.getNascimento(),
                    buffer.getRedesSociais().getTwitch(), buffer.getRedesSociais().getInstagram(), buffer.getRedesSociais().getYoutube(),
                    buffer.getMapaFavorito().getNome(),
                    buffer.getSkinFavorita().getNome(), buffer.getSkinFavorita().getArma()));
        }
        return ResponseEntity.notFound().build();
    }
}
