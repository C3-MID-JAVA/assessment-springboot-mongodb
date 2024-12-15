package org.example.financespro.strategy;

import java.math.BigDecimal;

public interface TransactionCostStrategy {
  BigDecimal calculateCost(BigDecimal amount);
}
