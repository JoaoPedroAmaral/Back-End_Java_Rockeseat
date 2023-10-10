package br.com.cursojava.tudolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name="tb_users")
public class UserModel {

    //UUID = identificador unico
    @Id //sempre selecionar o do jakarta devido o jakarta ser a dependencia do banco de dados
    @GeneratedValue(generator = "UUID")//gerar valor pro UUID
    private UUID id;

    /*@Column(name = "username") - cria uma coluna no banco de dados chamada name que irá se linkar com a variavel username */
    @Column(unique = true) // define que o valor de username deve ser unico, não repetido
    private String username;
    private String name;
    private String password;

    @CreationTimestamp //quando o dado for gerado ele vai mostrar a hora que foir criado
    private LocalDateTime createdAt;

}

/*EM CASO DE SER PRIVADOS UTILIZAMOS OS METODOS:
 *  
 * GETTERS - Buscar informação pro atributo
 * 
 * SETTERS - Inserir valor pro atributo
 */