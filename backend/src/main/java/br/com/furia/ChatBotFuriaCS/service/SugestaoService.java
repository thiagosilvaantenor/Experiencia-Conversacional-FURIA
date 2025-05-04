package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import br.com.furia.ChatBotFuriaCS.repository.SugestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
