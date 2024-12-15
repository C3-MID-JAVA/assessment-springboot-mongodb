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
    Account account = new Account();
    account.setAccountNumber(requestDTO.getAccountNumber());
    account.setBalance(BigDecimal.valueOf(requestDTO.getInitialBalance()));
    account = accountRepository.save(account);
    return AccountMapper.toResponseDTO(account);
  }

  @Override
  public AccountResponseDto getAccountById(Long id) {
    return accountRepository
        .findById(id)
        .map(AccountMapper::toResponseDTO)
        .orElseThrow(() -> new CustomException("Account not found"));
  }
}
