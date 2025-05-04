package br.com.furia.ChatBotFuriaCS.service;

import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import br.com.furia.ChatBotFuriaCS.repository.SkinFavoritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkinFavoritaService {
    @Autowired
    private SkinFavoritaRepository repository;

    public SkinFavorita salvar(SkinFavorita skin){
        if (skin != null) {
            return  repository.save(skin);
        }
        return null;
    }

    public List<SkinFavorita> buscarTodas(){
        return repository.findAll();
    }

    public Optional<SkinFavorita> buscarPeloId(Integer id) {
        return repository.findById(id);
    }
}
