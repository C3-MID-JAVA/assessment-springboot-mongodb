package org.example.financespro.service;

import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;

public interface TransactionService {

  /**
   * Processes a financial transaction.
   *
   * @param requestDTO the transaction details
   * @return the transaction response with cost and remaining balance
   */
  TransactionResponseDto processTransaction(TransactionRequestDto requestDTO);
}
