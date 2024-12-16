package org.example.financespro.strategy;

import java.math.BigDecimal;
import org.example.financespro.model.TransactionType;

/** Strategy for physical purchase transactions. */
public class PhysicalPurchaseStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.ZERO;
  }

  @Override
  public TransactionType getType() {
    return TransactionType.PHYSICAL_PURCHASE;
  }
}
