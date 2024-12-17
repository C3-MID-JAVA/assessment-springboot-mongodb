package org.bankAccountManager.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "account")
public class Account {
    @Id
    private int id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    @DBRef
    private List<Card> cards;
    @DBRef
    private List<Transaction> transactions;

    public Account(String accountNumber, String accountType, BigDecimal balance, List<Card> cards, List<Transaction> transactions, int id) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
        this.id = id;
        this.cards = cards;
        this.transactions = transactions;
    }

    public Account() {
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
