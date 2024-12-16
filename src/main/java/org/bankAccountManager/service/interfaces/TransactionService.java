package org.bankAccountManager.service.interfaces;

import org.bankAccountManager.entity.Transaction;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Transaction transaction);

    Transaction getTransactionById(int id);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByBranchId(int branch_id);

    List<Transaction> getTransactionsByDestinationAccountId(int destination_account_id);

    List<Transaction> getTransactionsBySourceAccountId(int source_account_id);

    List<Transaction> getTransactionsByDate(Timestamp date);

    List<Transaction> getTransactionsByType(String type);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(Transaction transaction);
}
