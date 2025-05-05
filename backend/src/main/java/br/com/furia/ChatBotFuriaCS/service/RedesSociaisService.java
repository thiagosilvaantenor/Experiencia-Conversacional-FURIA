package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.repository.RedeSociaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Classe service, oferece os serviços do banco de dados da classe Jogador
@Service
public class RedesSociaisService {
    //repositorio que o Spring irá instânciar
    @Autowired
    private RedeSociaisRepository repository;
    //Método para salvar no banco de dados
    public RedesSociais salvar(RedesSociais redesSociais){
        //Verifica se a redeSociais recebida é nullo, se não for, salva no banco de dados
        if (redesSociais != null) {
            return repository.save(redesSociais);
        }
        //Se for nulo, não é possivel salvar então retorna nullo
        return null;
    }
    //Busca todos as RedesSocias e retorna tudo em lista
    public List<RedesSociais> buscarTodas(){
        return repository.findAll();
    }
    //Busca todas as RedesSociais que contenham o canal da twitch enviado, retorna em lista, dado que podem haver replicatas
    public List<RedesSociais> buscaPorTwitch(String twitch) { return  repository.findByTwitch(twitch);}
    //Procura um RedesSocias especifico pelo id, retorna em Optional, que pode ser verificado se contem RedesSocias ou nada
    public Optional<RedesSociais> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
