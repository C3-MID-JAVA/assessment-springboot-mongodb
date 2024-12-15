package org.example.financespro.strategy;

import java.math.BigDecimal;

public class OtherAccountDepositStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(1.50);
  }
}
