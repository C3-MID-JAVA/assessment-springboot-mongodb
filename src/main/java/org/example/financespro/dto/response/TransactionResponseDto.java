package org.example.financespro.dto.response;

import java.math.BigDecimal;

public class TransactionResponseDto {

  private String type;
  private BigDecimal amount;
  private BigDecimal transactionCost;
  private BigDecimal remainingBalance;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getTransactionCost() {
    return transactionCost;
  }

  public void setTransactionCost(BigDecimal transactionCost) {
    this.transactionCost = transactionCost;
  }

  public BigDecimal getRemainingBalance() {
    return remainingBalance;
  }

  public void setRemainingBalance(BigDecimal remainingBalance) {
    this.remainingBalance = remainingBalance;
  }
}
