package com.sofka.bank.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;
    private double amount;
    private double fee;
    private LocalDateTime date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", nullable = false)

    private BankAccount bankAccount;

    public Transaction() {}

    public Transaction(Long id, String transactionType, double amount, double fee, LocalDateTime date, String description, BankAccount bankAccount) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.description = description;
        this.bankAccount = bankAccount;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public double getFee() {
        return fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}