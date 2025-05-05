package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.repository.MapaFavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Classe service, oferece os serviços do banco de dados da classe Jogador
@Service
public class MapaFavoritoService {
    //repositorio que o Spring irá instânciar
    @Autowired
    private MapaFavoritoRepository repository;
    //Método para salvar no banco de dados
    public MapaFavorito salvar(MapaFavorito mapaFavorito){
        //Verifica se o mapa recebido é nullo, se não for, salva no banco de dados
        if (mapaFavorito != null) {
            return  repository.save(mapaFavorito);
        }
        //Se for nulo, não é possivel salvar então retorna nullo
        return null;
    }
    //Busca todos os MapasFavoritos e retorna tudo em lista
    public List<MapaFavorito> buscarTodas(){
        return repository.findAll();
    }
    //Busca todas os MapaFavoritos que contenham o nome enviado, retorna em lista, dado que podem haver replicatas
    public List<MapaFavorito> buscaPeloNome(String nome){
        return repository.findByNome(nome);
    }
    //Procura um Mapa especifico pelo id, retorna em Optional, que pode ser verificado se contem Mapa ou nada
    public Optional<MapaFavorito> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
