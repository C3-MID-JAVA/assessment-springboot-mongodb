package org.example.financespro.service.impl;

import java.math.BigDecimal;
import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.exception.CustomException;
import org.example.financespro.mapper.TransactionMapper;
import org.example.financespro.model.Account;
import org.example.financespro.model.Transaction;
import org.example.financespro.model.TransactionType;
import org.example.financespro.repository.AccountRepository;
import org.example.financespro.repository.TransactionRepository;
import org.example.financespro.service.TransactionService;
import org.example.financespro.strategy.StrategyFactory;
import org.example.financespro.strategy.TransactionCostStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final StrategyFactory strategyFactory;

  public TransactionServiceImpl(
      AccountRepository accountRepository,
      TransactionRepository transactionRepository,
      StrategyFactory strategyFactory) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.strategyFactory = strategyFactory;
  }

  @Override
  @Transactional
  public TransactionResponseDto processTransaction(TransactionRequestDto requestDTO) {
    // Validate input
    if (requestDTO.getAccountId() == null || requestDTO.getAccountId().isBlank()) {
      throw new CustomException("Account ID cannot be null or blank");
    }
    if (requestDTO.getTransactionAmount() == null
        || requestDTO.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new CustomException("Transaction amount must be greater than 0");
    }
    if (requestDTO.getTransactionType() == null || requestDTO.getTransactionType().isBlank()) {
      throw new CustomException("Transaction type cannot be null or blank");
    }

    // Validate account existence
    Account account =
        accountRepository
            .findById(requestDTO.getAccountId())
            .orElseThrow(
                () ->
                    new CustomException("Account not found with ID: " + requestDTO.getAccountId()));

    // Validate transaction type
    TransactionType transactionType;
    try {
      transactionType = TransactionType.valueOf(requestDTO.getTransactionType().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new CustomException("Invalid transaction type: " + requestDTO.getTransactionType());
    }

    // Retrieve the appropriate strategy
    TransactionCostStrategy strategy = strategyFactory.getStrategy(transactionType);
    BigDecimal transactionCost = strategy.calculateCost(requestDTO.getTransactionAmount());
    BigDecimal newBalance =
        account.getBalance().subtract(requestDTO.getTransactionAmount().add(transactionCost));

    // Validate sufficient balance
    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new CustomException("Insufficient funds for transaction");
    }

    // Perform transaction
    account.setBalance(newBalance);
    accountRepository.save(account);

    Transaction transaction = new Transaction();
    transaction.setAccountId(account.getId());
    transaction.setType(transactionType.name());
    transaction.setAmount(requestDTO.getTransactionAmount());
    transaction.setTransactionCost(transactionCost);
    transactionRepository.save(transaction);

    return TransactionMapper.toResponseDTO(transaction, newBalance);
  }
}
