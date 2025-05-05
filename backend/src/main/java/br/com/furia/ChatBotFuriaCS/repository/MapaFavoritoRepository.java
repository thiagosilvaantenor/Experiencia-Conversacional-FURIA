package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Interface repository utilizando JPA, informado a classe, tipo do ID
public interface MapaFavoritoRepository extends JpaRepository<MapaFavorito, Integer> {
    List<MapaFavorito> findByNome(String nome);
}
