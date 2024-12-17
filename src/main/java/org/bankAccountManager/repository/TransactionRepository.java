package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Branch;
import org.bankAccountManager.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, Long> {
    Transaction findTransactionById(int id);

    Boolean existsById(int id);

    List<Transaction> findAll();

    List<Transaction> findTransactionsByDestinationAccountId(int destination_account_id);

    List<Transaction> findTransactionsBySourceAccountId(int source_account_id);

    List<Transaction> findTransactionsByBranches(List<Branch> branches);

    List<Transaction> findTransactionsByDate(Timestamp date);

    List<Transaction> findTransactionsByType(String type);
}
