package br.com.furia.ChatBotFuriaCS.model.skin_favorita;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
//Anotação JPA para a classe ter o mapeamento com a entidade do banco de dados skin_favorita
@Entity
//Anotações do Lombok, para ser feito construtores, getters, setters e equals e hashcode da classe
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class SkinFavorita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Como o chat tera poucos jogadores cadastrados e como as cada jogador tera apenas 1 skinFavorita, com chances de repetir a mesma skin,
    // acredito que INT como ID seja o suficiente
    private Integer id;
    private String nome;
    private String arma;
    //Uma entidade skinFavorita para muitas entidade Jogador
    @OneToMany(mappedBy="skinFavorita")
    private Set<Jogador> jogadores = new HashSet<>();

}
