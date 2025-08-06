package org.example.springrecapprojekt_todobackend.service;

import org.example.springrecapprojekt_todobackend.dto.TodoDTO;
import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.repository.TodoRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepo repo;
    private final IdService idService;

    public TodoService(TodoRepo repo, IdService idService) {
        this.repo = repo;
        this.idService = idService;
    }
    public List<Todo> getAllTodos() {
        return repo.findAll();
    }
    public ResponseEntity<Todo> getTodoById(String id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    public Todo createTodo(TodoDTO todoDTO) {
        Todo todo = new Todo(
                idService.generateId(),
                todoDTO.description(),
                todoDTO.status()
        );
        return repo.save(todo);
    }
    public Todo updateTodo(String id, TodoDTO updatedTodoDTO) {
        Todo todoToUpdate = new Todo(id,  updatedTodoDTO.description(), updatedTodoDTO.status());
        return  repo.save(todoToUpdate);
    }

    public ResponseEntity<Void> deleteTodo(String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
