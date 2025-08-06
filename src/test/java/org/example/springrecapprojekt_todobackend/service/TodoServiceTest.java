package org.example.springrecapprojekt_todobackend.service;

import org.example.springrecapprojekt_todobackend.dto.TodoDTO;
import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.example.springrecapprojekt_todobackend.model.TodoStatus.OPEN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TodoServiceTest {


    TodoRepo todoRepo = Mockito.mock(TodoRepo.class);
    IdService idService = Mockito.mock(IdService.class);
    TodoService todoService = new TodoService(todoRepo, idService);

    @Test
    void getAllTodos() {
        //GIVEN
        Todo t1 = new Todo("1", "Auto", OPEN);
        Todo t2 = new Todo("1", "Auto", OPEN);
        Todo t3 = new Todo("1", "Auto", OPEN);
        List <Todo> todos = List.of(t1, t2, t3);
        when(todoRepo.findAll()).thenReturn(todos);
        //WHEN
        List<Todo> actual = todoService.getAllTodos();
        //THEN
        verify(todoRepo).findAll();
        assertEquals(todos, actual);
    }

    @Test
    void createTodo() {
        // GIVEN
        // Erstelle ein TodoDTO mit Beschreibung "Urlaub" und Status OPEN
        TodoDTO dto = new TodoDTO("Urlaub", OPEN);
        // Erstelle ein erwartetes expectedTodo
        Todo expectedTodo = new Todo("test-id-123", "Urlaub", OPEN);
        // MOCK: Wenn idService.generateId() aufgerufen wird, soll "test-id-123" zurückgegeben werden
        when(idService.generateId()).thenReturn("test-id-123");
        // MOCK: Wenn todoRepo.save() mit genau dem Objekt expectedTodo aufgerufen wird, soll expectedTodo zurückgegeben werden
        when(todoRepo.save(expectedTodo)).thenReturn(expectedTodo);
        // WHEN
        // Die createTodo Methode mit dto aufrufen, Ergebnis in actual speichern
        Todo actual = todoService.createTodo(dto);
        // THEN
        // Prüfen, dass idService.generateId() genau einmal aufgerufen wurde
        verify(idService).generateId();
        // Prüfen, dass todoRepo.save() genau mit expectedTodo aufgerufen wurde
        verify(todoRepo).save(expectedTodo);
        // THEN: Prüfen, ob das zurückgegebene Todo genau dem expectedTodo entspricht
        assertEquals(expectedTodo, actual);
    }
}