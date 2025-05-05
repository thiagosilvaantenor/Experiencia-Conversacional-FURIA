package br.com.furia.ChatBotFuriaCS.model.mapa_favorito;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
//Anotação JPA para a classe ter o mapeamento com a entidade do banco de dados mapa_Favorito
@Entity
//Anotações do Lombok, para ser feito construtores, getters, setters e equals e hashcode da classe
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MapaFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Como o chat tera poucos jogadores cadastrados e cada jogador tera apenas 1 mapaFavorito, com chances de repetir o mesmo mapa,
    // acredito que INT como ID seja o suficiente
    private Integer id;
    private String nome;
    //Uma entidade mapaFavorito para muitas entidade Jogador
    @OneToMany(mappedBy = "mapaFavorito")
    private Set<Jogador> jogadores = new HashSet<>();
    // Construtor com apenas 1 argumento para lidar com o id que será gerado automaticamente pelo banco de dados
    public MapaFavorito(String nomeMapa) {
        this.nome = nomeMapa;
    }
}
