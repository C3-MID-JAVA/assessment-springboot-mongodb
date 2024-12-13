package com.sofka.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name= "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false, name = "account_holder")
    private String accountHolder;

    @Column(nullable = false, name = "global_balance", columnDefinition = "DECIMAL(10,2) CHECK (global_balance >= 0)")
    private double globalBalance;

    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity =
            Transaction.class)
    @JsonIgnore
    private List<Transaction> transactions;

    public BankAccount() {}

    public BankAccount(Long id, String accountNumber, String accountHolder, double globalBalance,
                       List<Transaction> transactions) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.globalBalance = globalBalance;
        this.transactions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getGlobalBalance() {
        return globalBalance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setGlobalBalance(double globalBalance) {
        this.globalBalance = globalBalance;

    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}