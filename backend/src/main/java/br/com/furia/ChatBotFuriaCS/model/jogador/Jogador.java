package br.com.furia.ChatBotFuriaCS.model.jogador;


import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity(name = "jogador")
@Table(name = "jogador")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "nickName")
public class Jogador {
    @Id
    @Column(name = "nick_name")
    private String nickName;
    private String nome;
    private LocalDate nascimento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "redesSociais_id")
    private RedesSociais redesSociais;
    @ManyToOne
    @JoinColumn(name = "mapaFavorito_id", nullable = false)
    private MapaFavorito mapaFavorito;
    @ManyToOne
    @JoinColumn(name = "skinFavorita_id", nullable = false)
    private SkinFavorita skinFavorita;
}
