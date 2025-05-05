package br.com.furia.ChatBotFuriaCS.model.sugestao;


import jakarta.persistence.*;
import lombok.*;
//Anotação JPA para a classe ter o mapeamento com a entidade do banco de dados sugestao
@Entity(name = "sugestao")
//Garante que a tabela vai ser criada como suegestao
@Table(name = "sugestao")
//Anotações do Lombok, para ser feito construtores, getters, setters e equals e hashcode da classe
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Sugestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //mapeia e garante o nome da coluna do id sera: sugestao_id
    @Column(name="sugestao_id")
    //Como o chat pode ter inumeras interações com usuários, e o mesmo usuário pode criar diversas sugestões
    // acredito que Long como ID seja necessario
    private Long id;
    private String tipo;
    private String descricao;
    //garante que o nome da coluna do emailUsuario sera: email_usuario
    @Column(name = "email_usuario")
    private String emailUsuario;
}
