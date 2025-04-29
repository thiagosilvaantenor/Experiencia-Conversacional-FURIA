package br.com.furia.ChatBotFuriaCS.model.mapa_favorito;

import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class MapaFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    @OneToMany(mappedBy = "mapaFavorito")
    private Set<Jogador> jogadores = new HashSet<>();
}
