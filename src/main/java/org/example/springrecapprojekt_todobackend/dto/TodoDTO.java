package dto;

import model.TodoStatus;

public record TodoDTO(String description,TodoStatus status) {}
