package org.example.financespro.strategy;

import java.math.BigDecimal;

public class PhysicalPurchaseStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.ZERO;
  }
}
