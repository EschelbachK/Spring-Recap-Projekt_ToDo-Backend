package org.example.springrecapprojekt_todobackend.service;

import org.example.springrecapprojekt_todobackend.dto.TodoDTO;
import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.repository.TodoRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
    public Todo getTodoById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Todo with id " + id + " not found"));
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

    public void deleteTodo(String id) {
      repo.deleteById(id);
    }
}
