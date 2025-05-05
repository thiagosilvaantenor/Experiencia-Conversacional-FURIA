package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;
//Interface repository utilizando JPA, informado a classe, tipo do ID
public interface JogadorRepository extends JpaRepository<Jogador, String> {
}
