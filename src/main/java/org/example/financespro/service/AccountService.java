package org.example.financespro.service;

import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;

public interface AccountService {
  AccountResponseDto createAccount(AccountRequestDto requestDTO);

  AccountResponseDto getAccountById(Long id);
}
