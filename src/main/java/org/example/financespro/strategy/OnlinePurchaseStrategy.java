package org.example.financespro.strategy;

import java.math.BigDecimal;
import org.example.financespro.model.TransactionType;

/** Strategy for online purchase transactions. */
public class OnlinePurchaseStrategy implements TransactionCostStrategy {

  @Override
  public BigDecimal calculateCost(BigDecimal amount) {
    return BigDecimal.valueOf(5.00);
  }

  @Override
  public TransactionType getType() {
    return TransactionType.ONLINE_PURCHASE;
  }
}
