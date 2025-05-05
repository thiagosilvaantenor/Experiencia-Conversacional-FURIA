package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Interface repository utilizando JPA, informado a classe, tipo do ID
public interface RedeSociaisRepository extends JpaRepository<RedesSociais, Integer> {
    List<RedesSociais> findByTwitch(String twitch);
}
