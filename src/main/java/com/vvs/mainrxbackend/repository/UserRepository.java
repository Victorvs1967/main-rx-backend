package com.vvs.mainrxbackend.repository;

import com.vvs.mainrxbackend.model.User;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, ObjectId> {
  Mono<User> findByUsername(String username);
  Mono<User> findById(String id);
  Mono<User> save(User user);
}
