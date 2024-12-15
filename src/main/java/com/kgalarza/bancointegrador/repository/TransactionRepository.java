package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
