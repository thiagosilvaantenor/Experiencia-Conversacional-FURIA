package br.com.furia.ChatBotFuriaCS.model.jogador;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavoritoService;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociaisService;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavoritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {
    @Autowired
    private JogadorRepository repository;

    @Autowired
    private RedesSociaisService redesService;

    @Autowired
    private MapaFavoritoService mapaService;

    @Autowired
    private SkinFavoritaService skinService;



    public Jogador salvar(Jogador jogador) {
        if (jogador != null) {

            if (jogador.getMapaFavorito() != null) {
                MapaFavorito mapa = jogador.getMapaFavorito();
                mapa.getJogadores().add(jogador);
                mapaService.salvar(mapa);
            }
            if (jogador.getSkinFavorita() != null) {
                SkinFavorita skin = jogador.getSkinFavorita();
                skin.getJogadores().add(jogador);
                skinService.salvar(jogador.getSkinFavorita());
            }

            return repository.save(jogador);
        }
        return null;
    }

    public List<Jogador> buscarTodos(){
        return repository.findAll();
    }

    public Optional<Jogador> buscarPeloId(String nickName) {
        return repository.findById(nickName);
    }
}
