package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Interface repository utilizando JPA, informado a classe, tipo do ID
public interface SkinFavoritaRepository extends JpaRepository<SkinFavorita, Integer> {
    List<SkinFavorita> findByNomeAndArma(String nome, String arma);
}
