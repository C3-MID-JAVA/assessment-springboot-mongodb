package org.example.financespro.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/** DTO for transaction requests. */
public class TransactionRequestDto {

  @NotBlank(message = "Account ID is required.")
  private String accountId;

  @NotBlank(message = "Transaction type is required.")
  private String transactionType;

  @NotNull(message = "Transaction amount is required.")
  @DecimalMin(value = "0.01", inclusive = false, message = "Transaction amount must be positive.")
  private BigDecimal transactionAmount;

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  public BigDecimal getTransactionAmount() {
    return transactionAmount;
  }

  public void setTransactionAmount(BigDecimal transactionAmount) {
    this.transactionAmount = transactionAmount;
  }
}
