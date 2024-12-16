package org.example.financespro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("transactions")
public class Transaction {

  @Id
  private String id;
  private String accountId;
  private String type;
  private BigDecimal amount;
  private BigDecimal transactionCost;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

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
}
