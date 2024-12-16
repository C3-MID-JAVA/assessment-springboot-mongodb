package com.sofka.bank.service;

import com.sofka.bank.dto.TransactionDTO;
import com.sofka.bank.entity.TransactionType;

public interface TransactionService {
    TransactionDTO registerTransaction(String accountId, TransactionDTO transactionDTO);
    Double getGlobalBalance(String accountId);
}