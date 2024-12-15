package org.example.financespro.strategy;

import java.math.BigDecimal;

public class ATMDepositStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(2.00);
  }
}
