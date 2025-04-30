package br.com.furia.ChatBotFuriaCS.model.redes_sociais;


import br.com.furia.ChatBotFuriaCS.model.jogador.Jogador;
import jakarta.persistence.*;
import lombok.*;
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class RedesSociais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "redesSociais_id")
    private Integer id;
    private String twitch;
    private String instagram;
    private String youtube;

    @OneToOne(mappedBy = "redesSociais")
    private Jogador jogador;

}
