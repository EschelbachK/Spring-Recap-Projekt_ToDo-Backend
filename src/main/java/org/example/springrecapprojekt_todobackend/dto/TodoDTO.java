package org.example.springrecapprojekt_todobackend.dto;

import org.example.springrecapprojekt_todobackend.model.TodoStatus;

public record TodoDTO(String description,TodoStatus status) {}
