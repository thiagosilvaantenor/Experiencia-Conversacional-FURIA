package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import br.com.furia.ChatBotFuriaCS.repository.SugestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Classe service, oferece os serviços do banco de dados da classe Jogador
@Service
public class SugestaoService{
    //repositorio que o Spring irá instânciar
    @Autowired
    private SugestaoRepository repository;


    public Sugestao salvar(Sugestao sugestao) {
        //Verifica se o Sugestao recebida é nullo, se não for salva no banco de daos
        if(sugestao != null){
            return repository.save(sugestao);
        }
        //Se for nulo, não é possivel salvar então retorna nullo
        return null;
    }
    //Busca todos as Sugestao e retorna tudo em lista
    public List<Sugestao> buscaTodas() {
        return repository.findAll();
    }
    //Procura um Sugestao especifico pelo id, retorna em Optional, que pode ser verificado se contem Sugestao ou nada
    public Optional<Sugestao> buscaPorId(Long id) {
        return repository.findById(id);
    }
}
