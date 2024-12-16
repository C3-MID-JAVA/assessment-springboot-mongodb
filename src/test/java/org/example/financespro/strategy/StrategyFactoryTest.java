package org.example.financespro.strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import org.example.financespro.exception.CustomException;
import org.example.financespro.model.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyFactoryTest {

  private StrategyFactory strategyFactory;

  @BeforeEach
  void setUp() {
    List<TransactionCostStrategy> strategies =
        List.of(new ATMDepositStrategy(), new DepositStrategy(), new OnlinePurchaseStrategy());
    strategyFactory = new StrategyFactory(strategies);
  }

  @Test
  void shouldReturnCorrectStrategy() {
    TransactionCostStrategy strategy = strategyFactory.getStrategy(TransactionType.ATM_DEPOSIT);
    assertNotNull(strategy);
    assertEquals(BigDecimal.valueOf(2.00), strategy.calculateCost(BigDecimal.TEN));
  }

  @Test
  void shouldThrowExceptionForUnsupportedType() {
    Exception exception =
        assertThrows(
            CustomException.class,
            () -> strategyFactory.getStrategy(TransactionType.PHYSICAL_PURCHASE));
    assertEquals("Unsupported transaction type: PHYSICAL_PURCHASE", exception.getMessage());
  }
}
