package org.bankAccountManager.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Account(String accountNumber, String accountType, BigDecimal balance, Customer customer, int id) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.customer = customer;
        this.id = id;
    }

    public Account() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String account_number) {
        this.accountNumber = account_number;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String account_type) {
        this.accountType = account_type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
