package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.sugestao.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;
//Interface repository utilizando JPA, informado a classe, tipo do ID
public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
}
