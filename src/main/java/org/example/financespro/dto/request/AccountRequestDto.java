package org.example.financespro.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** DTO for account creation requests. */
public class AccountRequestDto {

  @NotBlank(message = "Account number is required.")
  private String number;

  @NotNull(message = "Initial balance is required.")
  @DecimalMin(
      value = "0.0",
      inclusive = false,
      message = "Initial balance must be a positive value.")
  private BigDecimal initialBalance;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public BigDecimal getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(BigDecimal initialBalance) {
    this.initialBalance = initialBalance;
  }
}
