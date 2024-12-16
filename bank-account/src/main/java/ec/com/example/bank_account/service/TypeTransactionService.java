package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.TypeTransactionRequestDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;

import java.util.List;

public interface TypeTransactionService {

    TypeTransactionResponseDTO createTypeTransaction(TypeTransactionRequestDTO typeTransaction);

    List<TypeTransactionResponseDTO> getAllTypeTransactions();

}
