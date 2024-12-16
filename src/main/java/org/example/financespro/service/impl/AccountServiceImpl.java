package org.example.financespro.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

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
    if (requestDTO.getNumber() == null || requestDTO.getNumber().isBlank()) {
      throw new CustomException("Account number cannot be null or blank");
    }
    if (requestDTO.getInitialBalance() == null || requestDTO.getInitialBalance() < 0) {
      throw new CustomException("Initial balance must be a positive value");
    }


    Optional<Account> existingAccount = accountRepository.findByAccountNumber(requestDTO.getNumber());
    if (existingAccount.isPresent()) {
      throw new CustomException("Account with the same number already exists");
    }


    Account account = new Account();
    account.setAccountNumber(requestDTO.getNumber());
    account.setBalance(BigDecimal.valueOf(requestDTO.getInitialBalance()));
    account = accountRepository.save(account);

    return AccountMapper.toResponseDTO(account);
  }

  @Override
  public AccountResponseDto getAccountDetailsByNumber(String accountId) {

    if (accountId == null || accountId.isBlank()) {
      throw new CustomException("Account ID cannot be null or blank");
    }


    return accountRepository
            .findById(accountId)
            .map(AccountMapper::toResponseDTO)
            .orElseThrow(() -> new CustomException("Account not found with ID: " + accountId));
  }
}
