package org.example.financespro.mapper;

import java.math.BigDecimal;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.model.Transaction;

public class TransactionMapper {

  private TransactionMapper() {
    // Private constructor to prevent instantiation
  }

  public static TransactionResponseDto toResponseDTO(
      Transaction transaction, BigDecimal remainingBalance) {
    TransactionResponseDto response = new TransactionResponseDto();
    response.setType(transaction.getType());
    response.setAmount(transaction.getAmount());
    response.setTransactionCost(transaction.getTransactionCost());
    response.setRemainingBalance(remainingBalance);
    return response;
  }
}
