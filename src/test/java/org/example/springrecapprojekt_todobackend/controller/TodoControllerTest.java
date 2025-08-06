package org.example.springrecapprojekt_todobackend.controller;

import org.example.springrecapprojekt_todobackend.model.Todo;
import org.example.springrecapprojekt_todobackend.repository.TodoRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.example.springrecapprojekt_todobackend.model.TodoStatus.OPEN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    TodoRepo todoRepo;

    @Test
    void getAllTodos() throws Exception {
        //GIVEN
        //WHEN
        mockMvc.perform(MockMvcRequestBuilders.get("/api/todo"))
        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json ("""
                  []
                  """));

    }

    @Test
    void createTodo() throws Exception {
        // GIVEN
        // WHEN - Sende HTTP POST-Anfrage an Endpoint
        mockMvc.perform(post("/api/todo")
                        // Setze den "Content-Type" der Anfrage auf JSON
                        .contentType(MediaType.APPLICATION_JSON)
                        // Füge den JSON-Body mit Beschreibung und Status hinzu
                        .content("""
                    {
                      "description":"test-description",
                      "status": "OPEN"
                    }
                    """)
                )
                // THEN - Erwarte, dass die Antwort den HTTP-Status 200 OK hat
                .andExpect(status().isOk())
                // THEN - Erwarte, dass die Antwort im JSON-Format die Beschreibung und den Status enthält
                .andExpect(content().json("""
                    { "description":"test-description",
                      "status":"OPEN"
                    }
            """))
                // THEN - Erwarte, dass im JSON-Antwort-Body das Feld "id" vorhanden und nicht leer ist
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void updateTodo() throws Exception {
        // GIVEN
        Todo existingTodo = new Todo("1", "test-description", OPEN);
        todoRepo.save(existingTodo);
        // WHEN
        mockMvc.perform(put("/api/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "description":"test-description-2",
                          "status": "IN_PROGRESS"
                        }
                        """))
        // THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
              {
                  "id": "1",
                  "description": "test-description-2",
                  "status": "IN_PROGRESS"
              }
              """));
    }
}