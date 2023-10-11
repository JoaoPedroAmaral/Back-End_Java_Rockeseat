package br.com.cursojava.tudolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity(name = "tb_task")
public class TaskModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;
     
    @Column(length = 50) // definir limite de caractere
    private String title;
    private LocalDateTime statAt; // formato datetime: yyyy-mm-ddThh:mm:ss esse T é padrao do formato
    private LocalDateTime endAt;
    private String priority;

    
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
