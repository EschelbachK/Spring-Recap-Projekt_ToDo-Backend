package org.example.springrecapprojekt_todobackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

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
        //GIVEN

        //WHEN
        mockMvc.perform(post("/api/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                          "description":"test-description",
                          "status": "OPEN"
                        }
                        """)
        )
                .andExpect(status().isOk())
                .andExpect(content().json ("""
                        { "description":"test-description",
                          "status":"OPEN"
                        }
                """))
                .andExpect(jsonPath("$.id").isNotEmpty());

    }
}