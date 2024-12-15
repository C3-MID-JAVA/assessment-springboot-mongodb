package com.bankmanagement.bankmanagement.repository;

import com.bankmanagement.bankmanagement.model.Account;
import com.bankmanagement.bankmanagement.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findAllByAccountId(String accountId);
}
