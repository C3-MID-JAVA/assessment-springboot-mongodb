package com.sofka.bank.repository;

import com.sofka.bank.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    boolean existsByAccountNumber(String accountNumber);

}