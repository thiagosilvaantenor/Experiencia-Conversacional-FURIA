package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import br.com.furia.ChatBotFuriaCS.repository.SkinFavoritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Classe service, oferece os serviços do banco de dados da classe Jogador
@Service
public class SkinFavoritaService {
    //repositorio que o Spring irá instânciar
    @Autowired
    private SkinFavoritaRepository repository;

    //Método para salvar no banco de dados
    public SkinFavorita salvar(SkinFavorita skin){
        //Verifica se a skin recebida é nullo, se não for, salva no banco de dados
        if (skin != null) {
            return  repository.save(skin);
        }
        //Se for nulo, não é possivel salvar então retorna nullo
        return null;
    }
    //Busca todos as SkinFavoritas e retorna tudo em lista
    public List<SkinFavorita> buscarTodas(){
        return repository.findAll();
    }
    //Busca todas os SkinFavorita que contenham o nome da skin e o nome da arma enviado, retorna em lista, dado que podem haver replicatas
    public List<SkinFavorita> buscaPeloNomeEArma(String nome, String arma){
        return repository.findByNomeAndArma(nome, arma);
    }
    //Procura um SkinFavoritas especifico pelo id, retorna em Optional, que pode ser verificado se contem SkinFavoritas ou nada
    public Optional<SkinFavorita> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
