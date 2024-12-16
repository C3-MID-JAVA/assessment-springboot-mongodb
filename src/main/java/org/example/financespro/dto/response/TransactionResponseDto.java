package org.example.financespro.dto.response;

import java.math.BigDecimal;

public class TransactionResponseDto {

  private String transactionType;
  private BigDecimal transactionAmount;
  private BigDecimal transactionCost;
  private BigDecimal remainingBalance;

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
