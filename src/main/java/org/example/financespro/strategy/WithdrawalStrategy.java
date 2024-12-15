package org.example.financespro.strategy;

import java.math.BigDecimal;

public class WithdrawalStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(1.00);
  }
}
