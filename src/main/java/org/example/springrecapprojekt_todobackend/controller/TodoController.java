package controller;

import dto.TodoDTO;
import model.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.TodoRepo;
import service.IdService;

import java.util.List;

@RestController
@RequestMapping("/api/todo")

public class TodoController {

    private final TodoRepo todoRepository;
    private final IdService idService;

    public TodoController(TodoRepo todoRepository, IdService idService) {
        this.todoRepository = todoRepository;
        this.idService = idService;
    }

    // GET ALL - Alle gespeicherten Todos sollen in einer Liste zurückgeben werden
    @GetMapping
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    // GETBYID - inklusive <Optional> Prüfung
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        return todoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - TodoDTO mit generate UUID, nur Description und Status
    @PostMapping
    public Todo createTodo(@RequestBody TodoDTO todoDTO) {
        Todo todo = new Todo(
                idService.generateId(),
                todoDTO.description(),
                todoDTO.status()
        );
        return todoRepository.save(todo);
    }

    // PUT - Updated bestehendes mit OK oder wenn es nicht existiert, NOT FOUND aus
    // Das <Optional> wird aus dem MongoRepoInterface vererbt
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody TodoDTO updatedTodoDTO) {
        return todoRepository.findById(id)
                .map(existingTodo -> {
                    existingTodo.setDescription(updatedTodoDTO.description());
                    existingTodo.setStatus(updatedTodoDTO.status());
                    todoRepository.save(existingTodo);
                    return ResponseEntity.ok(existingTodo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - by ID mit OK oder wenn es nicht existiert, NOT FOUND aus

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
