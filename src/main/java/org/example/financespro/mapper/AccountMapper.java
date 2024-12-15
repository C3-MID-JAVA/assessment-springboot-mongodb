package org.example.financespro.mapper;

import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.model.Account;

public class AccountMapper {

  private AccountMapper() {
    // Private constructor to prevent instantiation
  }

  public static AccountResponseDto toResponseDTO(Account account) {
    AccountResponseDto response = new AccountResponseDto();
    response.setId(account.getId());
    response.setAccountNumber(account.getAccountNumber());
    response.setBalance(account.getBalance().doubleValue());
    return response;
  }
}
