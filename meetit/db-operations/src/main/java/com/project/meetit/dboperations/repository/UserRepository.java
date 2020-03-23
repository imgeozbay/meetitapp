package com.project.meetit.dboperations.repository;

import com.project.meetit.dboperations.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
}
