package org.example.financespro.mapper;

import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.model.Account;

public class AccountMapper {

  private AccountMapper() {}

  public static AccountResponseDto toResponseDTO(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Account cannot be null");
    }

    AccountResponseDto response = new AccountResponseDto();
    response.setId(account.getId());
    response.setAccountNumber(account.getAccountNumber());
    response.setBalance(account.getBalance().doubleValue());
    return response;
  }
}
