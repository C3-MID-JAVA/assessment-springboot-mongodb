package org.example.financespro.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountRequestDto {

  @NotBlank private String accountNumber;

  @NotNull private Double initialBalance;

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public Double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(Double initialBalance) {
    this.initialBalance = initialBalance;
  }
}
