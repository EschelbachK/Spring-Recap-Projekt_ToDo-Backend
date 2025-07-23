package org.example.springrecapprojekt_todobackend.repository;

import org.example.springrecapprojekt_todobackend.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepo extends MongoRepository<Todo, String> {
}
