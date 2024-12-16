package com.sofkau.usrv_accounts_manager.services;

import com.sofkau.usrv_accounts_manager.dto.TransactionDTO;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO transactionDTO);
}
