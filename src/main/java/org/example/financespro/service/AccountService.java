package org.example.financespro.service;

import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;

public interface AccountService {

  /**
   * Creates a new account.
   *
   * @param requestDTO the account creation request details
   * @return the created account details
   */
  AccountResponseDto createAccount(AccountRequestDto requestDTO);

  /**
   * Retrieves an account by its unique account number.
   *
   * @param accountNumber the unique account number
   * @return the account details
   */
  AccountResponseDto getAccountById(String accountNumber);
}
