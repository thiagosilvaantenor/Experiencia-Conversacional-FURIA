package br.com.furia.ChatBotFuriaCS.model.sugestao;


import jakarta.persistence.*;
import lombok.*;
@Entity(name = "sugestao")
@Table(name = "sugestao")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sugestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sugestao_id")
    private Long id;
    private String tipo;
    private String descricao;
    @Column(name = "email_usuario")
    private String emailUsuario;
}
