package br.com.furia.ChatBotFuriaCS.controller;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import br.com.furia.ChatBotFuriaCS.model.jogador.JogadorDTO;
import br.com.furia.ChatBotFuriaCS.service.JogadorService;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//RestController da api para lidar com Jogador em /jogador
@RestController
@RequestMapping("/jogador")
public class JogadorController {
    //Anotação para fazer o spring instanciar o service do jogador
    @Autowired
    private JogadorService service;

    //Método para criar jogador em /jogador enviando no body os dados de JogadorDTO
    @PostMapping
    public ResponseEntity<Jogador> criarJogador(@RequestBody JogadorDTO jogador) {
        //Para lidar com os relacionamentos com outras classes/entidades o DTO contem os dados para criar essas classes e o jogador

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

        //Cria o jogador com os dados do DTO e os objetos skin e mapa Favoritos
        Jogador novo = new Jogador(jogador.nickName(), jogador.nome(), jogador.nascimento(), redes, skin, mapa);


        //salva o jogador
        Jogador salvo = service.salvar(novo);

        //Verifica se o jogador for salvo
        if (salvo != null) {
            //Se sim, retorna código de status 201/created e no body o jogador em json
            return ResponseEntity.status(201).body(novo);
        }
        //Se não retorna badRequest/400
        return ResponseEntity.badRequest().build();
    }
    //Método para listar todos os jogadores, retorna uma lista de JogadorDTO
    @GetMapping
    public ResponseEntity<List<JogadorDTO>> listarTodos() {
        //Busca os jogadores salvos no banco de dados
        List<Jogador> jogadores = service.buscarTodos();
        //Cria a lista de DTOs
        List<JogadorDTO> jogadorDTOS = new ArrayList<>();
        //para cada jogador cria um DTO e adiciona na lista
        jogadores.forEach( jogador -> {
                 jogadorDTOS.add(new JogadorDTO(jogador.getNickName(), jogador.getNome(), jogador.getNascimento(),
                         jogador.getRedesSociais().getTwitch(), jogador.getRedesSociais().getInstagram(), jogador.getRedesSociais().getYoutube(),
                         jogador.getMapaFavorito().getNome(),
                         jogador.getSkinFavorita().getNome(), jogador.getSkinFavorita().getArma())
                 );
                });
        //retorna ok/200 e a lista de DTOs
        return ResponseEntity.ok(jogadorDTOS);
    }
    //Método para listar um jogador especifico pelo nickName dele
    @GetMapping("/{nickName}")
    public ResponseEntity<JogadorDTO> exibirJogador(@PathVariable String nickName) {
        //Busca o jogador no banco de dados é retornado um Optional
        Optional<Jogador> jogador = service.buscarPeloId(nickName);
        //Verifica se no Optional contém o jogador
        if (jogador.isPresent()){
            //Se sim, pega o jogador e com os dados dele cria um DTO pra ser retornado com ok/200
            Jogador buffer = jogador.get();
            return ResponseEntity.ok(new JogadorDTO(buffer.getNickName(), buffer.getNome(), buffer.getNascimento(),
                    buffer.getRedesSociais().getTwitch(), buffer.getRedesSociais().getInstagram(), buffer.getRedesSociais().getYoutube(),
                    buffer.getMapaFavorito().getNome(),
                    buffer.getSkinFavorita().getNome(), buffer.getSkinFavorita().getArma()));
        }
        //Caso o Optional não contenha um jogador, retorna notfound/404
        return ResponseEntity.notFound().build();
    }
    //Método para atualizar um jogador especifico, pode ser atualizado tudo dele ou partes, exceto o nickName pois é ID
    @PutMapping("/{nickName}")
    public ResponseEntity<Jogador> atualizarJogador(@PathVariable String nickName, @RequestBody JogadorDTO dados) {
        //Busca o jogador pelo nickName no banco de dados
        Optional<Jogador> jogador = service.buscarPeloId(nickName);
        //Verifica se Optional contém um Jogador e os dados para atualização não são nulos
        if(jogador.isPresent() && dados != null){
            //Se sim, pega o jogador do Optional
            Jogador encontrado = jogador.get();
            //Verifica em dados quais dados devem ser atualizados, atualiza o jogador encontrado com os dados
            if(dados.nome() != null){
                encontrado.setNome(dados.nome());
            }
            if(dados.nascimento() != null){
                encontrado.setNascimento(dados.nascimento());
            }
            if(dados.twitch() != null){
                encontrado.getRedesSociais().setTwitch(dados.twitch());
            }
            if(dados.instagram() != null){
                encontrado.getRedesSociais().setInstagram(dados.instagram());
            }
            if(dados.youtube() != null){
                encontrado.getRedesSociais().setYoutube(dados.youtube());
            }
            if(dados.nomeMapa() != null){
                encontrado.getMapaFavorito().setNome(dados.nomeMapa());
            }
            if(dados.nomeSkin() != null){
                encontrado.getSkinFavorita().setNome(dados.nomeSkin());
            }
            if(dados.arma() != null){
                encontrado.getSkinFavorita().setArma(dados.arma());
            }
            //Salva/Atualiza o jogador com os dados atualizados
            Jogador atualizado = service.salvar(encontrado);
            //retorna 200/ok e o jogador com todos os dados, incluindo os atualizados
            return ResponseEntity.ok(atualizado);
        }
        //Caso o Optional não contenha um jogador, ou, não tem dados para atualizar retorna badRequest/400
        return ResponseEntity.badRequest().build();
    }
}
