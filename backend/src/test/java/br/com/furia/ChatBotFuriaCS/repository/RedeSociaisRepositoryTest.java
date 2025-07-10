package br.com.furia.ChatBotFuriaCS.repository;

import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//Indica que vai testar o repository JPA
@DataJpaTest
@ActiveProfiles("test")
// Esta propriedade instrui o Flyway a limpar o schema antes de migrar.
//@TestPropertySource(properties = {"spring.flyway.clean-before-migrate=true"})
class RedeSociaisRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    RedeSociaisRepository redeSociaisRepository;


    @Test
    @DisplayName("deve buscar Redes Sociais com sucesso do banco de dados")
    void findRedeSociaisByTwitchCase1(){
        String twitch = "twitch.tv/teste";
        RedesSociais dados =
                new RedesSociais(null, twitch, "instagram/@teste",
                        "youtube.com/teste", null);
        this.criarRedeSocial(dados);
        List<RedesSociais> redesEncontradas = this.redeSociaisRepository.findByTwitch(twitch);
        //Verifica se encontrou pelo menos 1 rede social
        assertFalse(redesEncontradas.isEmpty());
    }


    private RedesSociais criarRedeSocial(RedesSociais dados){
        //Salva a rede social primeiro, para depois consultar no banco de dados
        RedesSociais novo = dados;
        this.entityManager.persist(novo);
        return novo;
    }
}