package com.sofka.bank.service;

import com.sofka.bank.dto.TransactionDTO;

public interface TransactionService {
    TransactionDTO registerTransaction(Long accountId, TransactionDTO transactionDTO);

    Double getGlobalBalance(Long accountId);
}