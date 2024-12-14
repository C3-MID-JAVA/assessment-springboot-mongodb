package com.bankmanagement.bankmanagement.repository;

import com.bankmanagement.bankmanagement.model.Account;
import com.bankmanagement.bankmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    List<Account> findAllByUser(User user);
    Account findByAccountNumber(String accountNumber);
}
