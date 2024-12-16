package org.example.financespro.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.exception.CustomException;
import org.example.financespro.model.Account;
import org.example.financespro.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AccountServiceImplTest {

  @Mock private AccountRepository accountRepository;

  @InjectMocks private AccountServiceImpl accountService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldThrowExceptionForInvalidInitialBalance() {
    AccountRequestDto requestDto = new AccountRequestDto();
    requestDto.setNumber("12345");
    requestDto.setInitialBalance(BigDecimal.valueOf(-100)); // Balance inválido

    // Ejecuta el test y verifica que lanza CustomException
    CustomException exception =
        assertThrows(CustomException.class, () -> accountService.createAccount(requestDto));
    assertEquals("Initial balance must be a positive value", exception.getMessage());

    // Verifica que no se llame al método save del repositorio
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  void shouldThrowExceptionWhenAccountNumberAlreadyExists() {
    AccountRequestDto requestDto = new AccountRequestDto();
    requestDto.setNumber("12345");
    requestDto.setInitialBalance(BigDecimal.valueOf(100));

    // Simula que la cuenta ya existe
    when(accountRepository.findByAccountNumber("12345")).thenReturn(Optional.of(new Account()));

    // Verifica que lanza CustomException
    CustomException exception =
        assertThrows(CustomException.class, () -> accountService.createAccount(requestDto));
    assertEquals("Account number already exists: 12345", exception.getMessage());

    // Verifica que no se llama a save
    verify(accountRepository, never()).save(any(Account.class));
  }

  @Test
  void shouldCreateAccountSuccessfully() {
    AccountRequestDto requestDto = new AccountRequestDto();
    requestDto.setNumber("12345");
    requestDto.setInitialBalance(BigDecimal.valueOf(1000));

    Account mockAccount = new Account();
    mockAccount.setId("1");
    mockAccount.setAccountNumber("12345");
    mockAccount.setBalance(BigDecimal.valueOf(1000));

    // Configura los mocks para la creación exitosa
    when(accountRepository.findByAccountNumber("12345")).thenReturn(Optional.empty());
    when(accountRepository.save(any(Account.class))).thenReturn(mockAccount);

    // Ejecuta el método y verifica el resultado
    accountService.createAccount(requestDto);

    // Verifica que save fue llamado
    verify(accountRepository, times(1)).save(any(Account.class));
  }

  @Test
  void shouldThrowExceptionForBlankAccountNumber() {
    AccountRequestDto requestDto = new AccountRequestDto();
    requestDto.setNumber(""); // Número de cuenta en blanco
    requestDto.setInitialBalance(BigDecimal.valueOf(1000));

    // Ejecuta el test y verifica que lanza CustomException
    CustomException exception =
        assertThrows(CustomException.class, () -> accountService.createAccount(requestDto));
    assertEquals("Account number cannot be null or blank", exception.getMessage());

    // Verifica que no se llama a save
    verify(accountRepository, never()).save(any(Account.class));
  }
}
