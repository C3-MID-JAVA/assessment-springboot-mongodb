package org.bankAccountManager.service.implementations;

import org.bankAccountManager.entity.Account;
import org.bankAccountManager.entity.Transaction;
import org.bankAccountManager.repository.AccountRepository;
import org.bankAccountManager.repository.TransactionRepository;
import org.bankAccountManager.service.interfaces.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImplementation implements TransactionService {

    private static final Map<String, BigDecimal> transactionTypes = new HashMap<>();

    static {
        transactionTypes.put("branch_transfer", new BigDecimal("0.00"));
        transactionTypes.put("another_account_deposit", new BigDecimal("1.50"));
        transactionTypes.put("store_card_purchase", new BigDecimal("0.00"));
        transactionTypes.put("online_card_purchase", new BigDecimal("5.00"));
        transactionTypes.put("atm_withdrawal", new BigDecimal("1.00"));
        transactionTypes.put("atm_deposit", new BigDecimal("2.00"));
    }

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionServiceImplementation(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        if (transactionRepository.existsById(transaction.getId()))
            throw new IllegalArgumentException("Transaction already exists");
        if (!transactionTypes.containsKey(transaction.getType()))
            throw new IllegalArgumentException("Invalid transaction type: " + transaction.getType());
        switch (transaction.getType()) {
            case "branch_transfer":
            case "another_account_deposit":
            case "atm_deposit":
                executeTransaction(transaction.getSourceAccount(),
                        transaction.getAmount().add(transactionTypes.get(transaction.getType())).negate());
                executeTransaction(transaction.getDestinationAccount(), transaction.getAmount());
                break;
            case "store_card_purchase":
            case "online_card_purchase":
            case "atm_withdrawal":
                executeTransaction(transaction.getDestinationAccount(),
                        transaction.getAmount().add(transactionTypes.get(transaction.getType())).negate());
                executeTransaction(transaction.getSourceAccount(), transaction.getAmount());
                break;
            default:
                throw new IllegalArgumentException("Unhandled transaction type: " + transaction.getType());
        }
        return transactionRepository.save(transaction);
    }

    private void executeTransaction(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0 && account.getBalance().compareTo(amount.abs()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in account: " + account.getId());
        }
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionRepository.findTransactionById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByBranchId(int branch_id) {
        return transactionRepository.findTransactionsByBranchId(branch_id);
    }

    @Override
    public List<Transaction> getTransactionsByDestinationAccountId(int destination_account_id) {
        return transactionRepository.findTransactionsByDestinationAccountId(destination_account_id);
    }

    @Override
    public List<Transaction> getTransactionsBySourceAccountId(int source_account_id) {
        return transactionRepository.findTransactionsBySourceAccountId(source_account_id);
    }

    @Override
    public List<Transaction> getTransactionsByDate(Timestamp date) {
        return transactionRepository.findTransactionsByDate(date);
    }

    @Override
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepository.findTransactionsByType(type);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }
}
