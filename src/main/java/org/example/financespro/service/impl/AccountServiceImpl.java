package org.example.financespro.service.impl;

import java.math.BigDecimal;
import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.exception.CustomException;
import org.example.financespro.mapper.AccountMapper;
import org.example.financespro.model.Account;
import org.example.financespro.repository.AccountRepository;
import org.example.financespro.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public AccountResponseDto createAccount(AccountRequestDto requestDTO) {
    // Validaciones explícitas
    if (requestDTO.getNumber() == null || requestDTO.getNumber().isBlank()) {
      throw new CustomException("Account number cannot be null or blank");
    }
    if (requestDTO.getInitialBalance() == null
        || requestDTO.getInitialBalance().compareTo(BigDecimal.ZERO) <= 0) {
      throw new CustomException("Initial balance must be a positive value");
    }

    // Comprueba si la cuenta ya existe
    if (accountRepository.findByAccountNumber(requestDTO.getNumber()).isPresent()) {
      throw new CustomException("Account number already exists: " + requestDTO.getNumber());
    }

    // Simula la creación de una cuenta
    Account account = new Account();
    account.setAccountNumber(requestDTO.getNumber());
    account.setBalance(requestDTO.getInitialBalance());

    Account savedAccount = accountRepository.save(account);

    // Valida el resultado de save
    if (savedAccount == null) {
      throw new CustomException("Account creation failed");
    }

    return AccountMapper.toResponseDTO(savedAccount);
  }

  @Override
  public AccountResponseDto getAccountDetailsByNumber(String accountNumber) {
    return accountRepository
        .findByAccountNumber(accountNumber)
        .map(AccountMapper::toResponseDTO)
        .orElseThrow(() -> new CustomException("Account not found with number: " + accountNumber));
  }
}
