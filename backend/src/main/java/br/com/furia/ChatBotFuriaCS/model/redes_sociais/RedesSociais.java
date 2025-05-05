package br.com.furia.ChatBotFuriaCS.model.redes_sociais;


import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import jakarta.persistence.*;
import lombok.*;
//Anotação JPA para a classe ter o mapeamento com a entidade do banco de dados redes_sociais
@Entity
//Anotações do Lombok, para ser feito construtores, getters, setters e equals e hashcode da classe
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RedesSociais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "redesSociais_id")
    //Como o chat tera poucos jogadores cadastrados e cada jogador tera apenas 1 entidade RedesSociais,
    // acredito que INT como ID seja o suficiente
    private Integer id;
    private String twitch;
    private String instagram;
    private String youtube;
//Uma entidade redeSociais para uma entidade Jogador
    @OneToOne(mappedBy = "redesSociais")
    private Jogador jogador;

}
