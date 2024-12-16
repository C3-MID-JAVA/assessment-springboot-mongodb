package org.example.financespro.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.exception.CustomException;
import org.example.financespro.model.Account;
import org.example.financespro.model.TransactionType;
import org.example.financespro.repository.AccountRepository;
import org.example.financespro.repository.TransactionRepository;
import org.example.financespro.strategy.StrategyFactory;
import org.example.financespro.strategy.TransactionCostStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransactionServiceImplTest {

  @Mock private AccountRepository accountRepository;

  @Mock private TransactionRepository transactionRepository;

  @Mock private StrategyFactory strategyFactory;

  @InjectMocks private TransactionServiceImpl transactionService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldProcessTransactionSuccessfully() {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("123");
    request.setTransactionType("ATM_WITHDRAWAL");
    request.setTransactionAmount(BigDecimal.valueOf(500));

    Account account = new Account();
    account.setId("123");
    account.setBalance(BigDecimal.valueOf(1000));

    // Mock del Strategy
    TransactionCostStrategy mockStrategy = mock(TransactionCostStrategy.class);
    when(mockStrategy.calculateCost(any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1.00));

    // Configurar mocks
    when(accountRepository.findById("123")).thenReturn(Optional.of(account));
    when(strategyFactory.getStrategy(TransactionType.ATM_WITHDRAWAL)).thenReturn(mockStrategy);
    when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // Ejecutar y verificar resultados
    TransactionResponseDto response = transactionService.processTransaction(request);

    // Convertimos ambos valores a BigDecimal
    assertEquals(
        new BigDecimal("499.0"),
        response.getRemainingBalance(),
        "Remaining balance should match expected value.");
    verify(accountRepository).save(account);
    verify(transactionRepository).save(any());
  }

  @Test
  void shouldThrowExceptionForInvalidTransactionType() {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("123");
    request.setTransactionType("INVALID_TYPE");
    request.setTransactionAmount(BigDecimal.valueOf(500));

    // Configura el mock para encontrar la cuenta
    Account account = new Account();
    account.setId("123");
    account.setBalance(BigDecimal.valueOf(1000));
    when(accountRepository.findById("123")).thenReturn(Optional.of(account));

    // No configuramos una estrategia válida para este tipo de transacción
    CustomException exception =
        assertThrows(CustomException.class, () -> transactionService.processTransaction(request));

    assertEquals("Invalid transaction type: INVALID_TYPE", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionForInsufficientFunds() {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("123");
    request.setTransactionType("ATM_WITHDRAWAL");
    request.setTransactionAmount(BigDecimal.valueOf(1500));

    Account account = new Account();
    account.setId("123");
    account.setBalance(BigDecimal.valueOf(1000));

    // Mock del Strategy
    TransactionCostStrategy mockStrategy = mock(TransactionCostStrategy.class);
    when(mockStrategy.calculateCost(any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1.00));

    // Configurar mocks
    when(accountRepository.findById("123")).thenReturn(Optional.of(account));
    when(strategyFactory.getStrategy(TransactionType.ATM_WITHDRAWAL)).thenReturn(mockStrategy);

    CustomException exception =
        assertThrows(CustomException.class, () -> transactionService.processTransaction(request));

    assertEquals("Insufficient funds for transaction", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenAccountNotFound() {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("123");
    request.setTransactionType("ATM_WITHDRAWAL");
    request.setTransactionAmount(BigDecimal.valueOf(500));

    when(accountRepository.findById("123")).thenReturn(Optional.empty());

    CustomException exception =
        assertThrows(CustomException.class, () -> transactionService.processTransaction(request));

    assertEquals("Account not found with ID: 123", exception.getMessage());
  }
}
