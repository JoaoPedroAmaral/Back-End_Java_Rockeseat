package br.com.cursojava.tudolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursojava.tudolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
        private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        taskModel.setIdUser((UUID)request.getAttribute("IdUser"));//pegando o IdUser e setando no taskModel

        //criando validações
        var currentDate = LocalDateTime.now();
        //caso a data seja menor que a data atual, retorna erro 
        if(currentDate.isAfter(taskModel.getStatAt()) || currentDate.isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio e data de termino deve ser maior que a data atual");
        }

        if(taskModel.getStatAt().isAfter(taskModel.getEndAt())){ 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de inicio deve ser menor que a data de termino");
        }        

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("IdUser");//pegando o IdUser e setando no taskModel
        var tasks = this.taskRepository.findByIdUser((UUID)idUser);
        return tasks;
    }//lista tarefas

    //update tarefa
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel,@PathVariable UUID id, HttpServletRequest request){
        var task = this.taskRepository.findById(id).orElse(null);
        var idUser = request.getAttribute("IdUser");//pegando o IdUser e setando no taskModel

        if(task == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tarefa não encontrada");  
        }

        if(!task.getIdUser().equals(idUser)/*se for diferente*/){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario não tem permissão para alterar esta tarefa");   
        }

        Utils.copyNonNullPropeties(taskModel, task);

        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
