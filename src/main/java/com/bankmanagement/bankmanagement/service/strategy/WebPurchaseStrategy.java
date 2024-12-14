package com.bankmanagement.bankmanagement.service.strategy;

import org.springframework.stereotype.Component;

@Component
public class WebPurchaseStrategy implements TransactionStrategy {
    @Override
    public double calculateFee() {
        return 5;
    }

    @Override
    public double calculateBalance(double balance, double amount) {
        double fee = calculateFee();
        double totalCost = amount + fee;
        if (balance < totalCost) {
            throw new RuntimeException("Insufficient balance for this transaction.");
        }
        return balance - totalCost;
    }
}
