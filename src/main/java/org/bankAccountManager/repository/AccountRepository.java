package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Account;
import org.bankAccountManager.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, Long> {
    Account findAccountById(int id);

    Boolean existsById(int id);

    List<Account> findAll();

    Account getAccountByTransactions(List<Transaction> transactions);
}
