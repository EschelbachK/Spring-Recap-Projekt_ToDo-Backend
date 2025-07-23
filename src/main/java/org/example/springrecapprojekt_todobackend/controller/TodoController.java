package org.example.springrecapprojekt_todobackend.controller;

import org.example.springrecapprojekt_todobackend.dto.TodoDTO;
import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    // GET ALL - Alle gespeicherten Todos sollen in einer Liste zurückgeben werden
    @GetMapping
    public List<Todo> getAllTodos() {
        return service .getAllTodos();
    }

    // GETBYID - inklusive <Optional> Prüfung
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable String id) {
        return service.getTodoById(id);
    }

    // POST - TodoDTO mit generate UUID, nur Description und Status
    @PostMapping
    public Todo createTodo(@RequestBody TodoDTO todoDTO) {
        return service.createTodo(todoDTO);
    }

    // PUT - Updated bestehendes mit OK oder wenn es nicht existiert, NOT FOUND aus
    // Das <Optional> wird aus dem MongoRepoInterface vererbt
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String id, @RequestBody TodoDTO updatedTodoDTO) {
        return service.updateTodo(id, updatedTodoDTO);
    }

    // DELETE - by ID mit OK oder wenn es nicht existiert, NOT FOUND aus
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String id) {
        return service.deleteTodo(id);
    }
}
