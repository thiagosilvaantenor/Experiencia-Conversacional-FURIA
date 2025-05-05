package br.com.furia.ChatBotFuriaCS.model.jogador;


import br.com.furia.ChatBotFuriaCS.model.mapa_favorito.MapaFavorito;
import br.com.furia.ChatBotFuriaCS.model.redes_sociais.RedesSociais;
import br.com.furia.ChatBotFuriaCS.model.skin_favorita.SkinFavorita;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
//Anotação JPA para a classe ter o mapeamento com a entidade do banco de dados jogador
@Entity(name = "jogador")
//Garante que o nome na tabela vai ser jogador
@Table(name = "jogador")
//Anotações do Lombok, para ser feito construtores, getters, setters e equals e hashcode da classe
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
    //Uma entidade jogador para uma entidade redeSocial
    // adicionado o cascade para o spring lidar com o insert na tabela redeSociais ao inserir um jogador com os dados necessários para ela
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "redesSociais_id")
    private RedesSociais redesSociais;
    //Muitas entidade Jogador para uma entidade skinFavorita
    @ManyToOne
    @JoinColumn(name = "skinFavorita_id", nullable = false)
    private SkinFavorita skinFavorita;
    //Muitas entidade Jogador para uma entidade mapaFavorito
    @ManyToOne
    @JoinColumn(name = "mapaFavorito_id", nullable = false)
    private MapaFavorito mapaFavorito;
}
