package org.example.financespro.strategy;

import java.math.BigDecimal;

public class OnlinePurchaseStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(5.00);
  }
}
