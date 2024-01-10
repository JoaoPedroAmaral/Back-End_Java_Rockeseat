package br.com.cursojava.tudolist.task;
/*
 Metodos de acesso HTTP
 * GET - Busca um dado/info
 * POST - Adiciona um dado/info
 * DELETE - Remove um dado/info
 * PUT - Altera um dado/info
 * PATCH - Altera apenas um dado/info
 */
import br.com.cursojava.tudolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        var initialTask = taskModel.getStartAt();
        var endTask = taskModel.getEndAt();

        if (currentDate.isAfter(initialTask) || currentDate.isAfter(endTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data de início ou término " +
                    "devem ser maior que data atual");
        }
        if (initialTask.isAfter(endTask)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("A data de início deve ser menor que a data de término");
        }
        var task = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.OK).body(task);
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);
        return tasks;
    }

    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var task = this.taskRepository.findById(id).orElse(null);
        Utils.copyNonNullPropeties(taskModel,task);
        return this.taskRepository.save(task);
    }

}