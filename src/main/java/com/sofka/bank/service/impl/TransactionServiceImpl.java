package com.sofka.bank.service.impl;

import com.sofka.bank.dto.TransactionDTO;
import com.sofka.bank.entity.BankAccount;
import com.sofka.bank.entity.Transaction;
import com.sofka.bank.exceptions.AccountNotFoundException;
import com.sofka.bank.exceptions.InsufficientFundsException;
import com.sofka.bank.mapper.DTOMapper;
import com.sofka.bank.repository.BankAccountRepository;
import com.sofka.bank.repository.TransactionRepository;
import com.sofka.bank.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  BankAccountRepository bankAccountRepository){
        this.transactionRepository = transactionRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    @Transactional
    public TransactionDTO registerTransaction(Long accountId, TransactionDTO transactionDTO) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));

        double fee = calculateFee(transactionDTO.getTransactionType());

        if (transactionDTO.getTransactionType().matches("WITHDRAW.*|ONLINE_PURCHASE|DEPOSIT_ATM " +
                "|DEPOSIT_OTHER_ACCOUNT") &&
                account.getGlobalBalance() < transactionDTO.getAmount() + fee) {
            throw new InsufficientFundsException("Insufficient balance for transaction");
        }


        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setFee(fee);
        transaction.setDate(LocalDateTime.now());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setBankAccount(account);


        account.setGlobalBalance(account.getGlobalBalance() - transactionDTO.getAmount() - fee);

        bankAccountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);


        return DTOMapper.toTransactionDTO(savedTransaction);
    }

    @Override
    public Double getGlobalBalance(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));
        return account.getGlobalBalance();
    }


    private double calculateFee(String type) {
        return switch (type) {
            case "DEPOSIT_ATM" -> 2.0;
            case "DEPOSIT_OTHER_ACCOUNT" -> 1.5;
            case "WITHDRAW_ATM" -> 1.0;
            case "ONLINE_PURCHASE" -> 5.0;
            default -> 0.0;
        };
    }

}