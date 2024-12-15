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
import org.example.financespro.strategy.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;
  private final StrategyContext strategyContext;

  public TransactionServiceImpl(
      AccountRepository accountRepository,
      TransactionRepository transactionRepository,
      StrategyContext strategyContext) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
    this.strategyContext = strategyContext;
  }

  @Override
  @Transactional
  public TransactionResponseDto processTransaction(TransactionRequestDto requestDTO) {
    Account account =
        accountRepository
            .findById(requestDTO.getAccountId())
            .orElseThrow(() -> new CustomException("Account not found"));

    TransactionType transactionType;
    try {
      transactionType = TransactionType.valueOf(requestDTO.getType().toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new CustomException("Invalid transaction type");
    }

    TransactionCostStrategy strategy = determineStrategy(transactionType);
    strategyContext.setStrategy(strategy);

    BigDecimal transactionCost = strategyContext.executeStrategy(requestDTO.getAmount());
    BigDecimal newBalance =
        account.getBalance().subtract(requestDTO.getAmount().add(transactionCost));

    if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new CustomException("Insufficient funds");
    }

    account.setBalance(newBalance);
    accountRepository.save(account);

    Transaction transaction = new Transaction();
    transaction.setAccount(account);
    transaction.setType(transactionType.name());
    transaction.setAmount(requestDTO.getAmount());
    transaction.setTransactionCost(transactionCost);

    transactionRepository.save(transaction);

    return TransactionMapper.toResponseDTO(transaction, newBalance);
  }

  private TransactionCostStrategy determineStrategy(TransactionType type) {
    return switch (type) {
      case BRANCH_DEPOSIT -> new DepositStrategy();
      case ATM_DEPOSIT -> new ATMDepositStrategy();
      case ONLINE_DEPOSIT -> new OtherAccountDepositStrategy();
      case PHYSICAL_PURCHASE -> new PhysicalPurchaseStrategy();
      case ONLINE_PURCHASE -> new OnlinePurchaseStrategy();
      case ATM_WITHDRAWAL -> new WithdrawalStrategy();
      default -> throw new CustomException("Unsupported transaction type");
    };
  }
}
