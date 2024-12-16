package com.sofka.bank.repository;

import com.sofka.bank.entity.BankAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
    boolean existsByAccountNumber(String accountNumber);

}