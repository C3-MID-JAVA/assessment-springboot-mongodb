package org.example.financespro.strategy;

import java.math.BigDecimal;
import org.example.financespro.model.TransactionType;

/** Interface for transaction cost strategies. */
public interface TransactionCostStrategy {
  BigDecimal calculateCost(BigDecimal amount);

  /**
   * Returns the associated transaction type for the strategy.
   *
   * @return the TransactionType
   */
  TransactionType getType();
}
