package org.example.springrecapprojekt_todobackend.service;

import org.example.springrecapprojekt_todobackend.dto.TodoDTO;
import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.example.springrecapprojekt_todobackend.model.TodoStatus.IN_PROGRESS;
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
        List<Todo> todos = List.of(t1, t2, t3);
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

    @Test
    void updateTodo() {
        // GIVEN
        String id = "123";
        TodoDTO dto = new TodoDTO("Haus", OPEN);
        Todo expectedTodo = new Todo(id, "Haus", OPEN);
        when(todoRepo.save(expectedTodo)).thenReturn(expectedTodo);
        // WHEN
        Todo actual = todoService.updateTodo(id, dto);
        // THEN
        verify(todoRepo).save(expectedTodo);
        assertEquals(expectedTodo, actual);
    }
    @Test
    void getTodoById_Test_whenValidId_ThenReturnTodo() {
        //GIVEN
        String id = "1";
        Todo todo = new Todo("1", "test-description", OPEN);
        when(todoRepo.findById(id)).thenReturn(Optional.of(todo));
        //WHEN
        Todo actual = todoService.getTodoById(id);
        //THEN
        verify(todoRepo).findById(id);
        assertEquals(todo, actual);
    }

    @Test
    void getTodoById_Test_whenInvalidId_ThenReturnException() {
        //GIVEN
        String id = "1";
        when(todoRepo.findById(id)).thenReturn(Optional.empty());
        //WHEN
        assertThrows(NoSuchElementException.class, () -> todoService.getTodoById(id));
        //THEN
        verify(todoRepo).findById(id);
    }
}