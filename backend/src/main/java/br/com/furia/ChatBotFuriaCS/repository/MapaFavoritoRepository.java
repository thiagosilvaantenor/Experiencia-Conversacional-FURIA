package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapaFavoritoRepository extends JpaRepository<MapaFavorito, Integer> {
}
