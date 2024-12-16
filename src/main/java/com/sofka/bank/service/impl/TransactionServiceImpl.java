package com.sofka.bank.service.impl;

import com.sofka.bank.dto.TransactionDTO;
import com.sofka.bank.entity.BankAccount;
import com.sofka.bank.entity.Transaction;
import com.sofka.bank.entity.TransactionType;
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
    public TransactionDTO registerTransaction(String accountId, TransactionDTO transactionDTO) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));

        double fee = transactionDTO.getTransactionType().getFee();

        if (transactionDTO.getTransactionType() == TransactionType.WITHDRAW_ATM ||
                transactionDTO.getTransactionType() == TransactionType.ONLINE_PURCHASE ||
                transactionDTO.getTransactionType() == TransactionType.DEPOSIT_ATM ||
                transactionDTO.getTransactionType() == TransactionType.DEPOSIT_OTHER_ACCOUNT ||
                transactionDTO.getTransactionType() == TransactionType.BRANCH_DEPOSIT ||
                        transactionDTO.getTransactionType() == TransactionType.ONSITE_CARD_PURCHASE){

            if (account.getGlobalBalance() < transactionDTO.getAmount() + fee) {
                throw new InsufficientFundsException("Insufficient balance for transaction");
            }
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
    public Double getGlobalBalance(String accountId) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with ID " + accountId + " not found"));
        return account.getGlobalBalance();
    }


}