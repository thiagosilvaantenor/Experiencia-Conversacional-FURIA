package br.com.furia.ChatBotFuriaCS.model.sugestao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SugestaoService{
    @Autowired
    private SugestaoRepository repository;

    public Sugestao salvar(Sugestao sugestao) {
        return repository.save(sugestao);
    }

    public List<Sugestao> buscaTodas() {
        return repository.findAll();
    }

    public Optional<Sugestao> buscaPorId(Long id) {
        return repository.findById(id);
    }
}
