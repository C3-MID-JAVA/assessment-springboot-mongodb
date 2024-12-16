package org.example.financespro.strategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.financespro.exception.CustomException;
import org.example.financespro.model.TransactionType;
import org.springframework.stereotype.Service;

/** Factory to manage transaction cost strategies dynamically. */
@Service
public class StrategyFactory {

  private final Map<TransactionType, TransactionCostStrategy> strategies;

  /**
   * Initializes the factory with available strategies.
   *
   * @param strategyList list of TransactionCostStrategy beans
   */
  public StrategyFactory(List<TransactionCostStrategy> strategyList) {
    // Converts the list of strategies into a map using their associated TransactionType
    this.strategies =
        strategyList.stream()
            .collect(Collectors.toMap(TransactionCostStrategy::getType, Function.identity()));
  }

  /**
   * Retrieves the appropriate strategy for the given transaction type.
   *
   * @param type the TransactionType for which the strategy is required
   * @return the matching TransactionCostStrategy
   */
  public TransactionCostStrategy getStrategy(TransactionType type) {
    return Optional.ofNullable(strategies.get(type))
        .orElseThrow(() -> new CustomException("Unsupported transaction type: " + type));
  }
}
