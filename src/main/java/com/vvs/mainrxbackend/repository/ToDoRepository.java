package com.vvs.mainrxbackend.repository;

import com.vvs.mainrxbackend.model.ToDo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends ReactiveMongoRepository<ToDo, String> {
}
