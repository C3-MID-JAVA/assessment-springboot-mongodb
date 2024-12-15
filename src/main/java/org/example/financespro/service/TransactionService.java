package org.example.financespro.service;

import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;

public interface TransactionService {
  TransactionResponseDto processTransaction(TransactionRequestDto requestDTO);
}
