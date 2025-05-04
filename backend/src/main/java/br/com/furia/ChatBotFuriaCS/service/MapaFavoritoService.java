package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.repository.MapaFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapaFavoritoService {
    @Autowired
    private MapaFavoritoRepository repository;

    public MapaFavorito salvar(MapaFavorito mapaFavorito){
        if (mapaFavorito != null) {
            return  repository.save(mapaFavorito);
        }
        return null;
    }

    public List<MapaFavorito> buscarTodas(){
        return repository.findAll();
    }

    public Optional<MapaFavorito> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
