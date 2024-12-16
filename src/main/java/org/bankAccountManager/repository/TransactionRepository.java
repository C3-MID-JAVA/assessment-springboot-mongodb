package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findTransactionById(int id);

    Boolean existsById(int id);

    List<Transaction> findAll();

    List<Transaction> findTransactionsByBranchId(int branch_id);

    List<Transaction> findTransactionsByDestinationAccountId(int destination_account_id);

    List<Transaction> findTransactionsBySourceAccountId(int source_account_id);

    List<Transaction> findTransactionsByDate(Timestamp date);

    List<Transaction> findTransactionsByType(String type);
}
