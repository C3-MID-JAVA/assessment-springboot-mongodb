package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.TransactionRequestDTO;
import ec.com.example.bank_account.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO);

    List<TransactionResponseDTO> getAllTransactions();

}