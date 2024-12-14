package com.bankmanagement.bankmanagement.repository;

import com.bankmanagement.bankmanagement.model.Account;
import com.bankmanagement.bankmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByAccount(Account account);
}
