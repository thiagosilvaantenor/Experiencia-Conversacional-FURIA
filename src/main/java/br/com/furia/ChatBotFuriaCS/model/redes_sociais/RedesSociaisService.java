package br.com.furia.ChatBotFuriaCS.model.redes_sociais;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedesSociaisService {
    @Autowired
    private RedeSociaisRepository repository;

    public RedesSociais salvar(RedesSociais redesSociais){
        if (redesSociais != null) {
            return  repository.save(redesSociais);
        }
        return null;
    }

    public List<RedesSociais> buscarTodas(){
        return repository.findAll();
    }

    public Optional<RedesSociais> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
