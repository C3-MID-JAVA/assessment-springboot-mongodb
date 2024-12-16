package com.bankmanagement.bankmanagement.repository;

import com.bankmanagement.bankmanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByDocumentId(String documentId);
}
