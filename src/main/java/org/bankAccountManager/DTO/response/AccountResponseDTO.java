package org.bankAccountManager.DTO.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.bankAccountManager.entity.Card;
import org.bankAccountManager.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class AccountResponseDTO {
    private int id;
    private String account_number;
    private String account_type;
    private BigDecimal balance;
    private List<CardResponseDTO> cards;
    private List<TransactionResponseDTO> transactions;

    public AccountResponseDTO(String account_number, String account_type, BigDecimal balance, List<CardResponseDTO> cards, List<TransactionResponseDTO> transactions, int id) {
        this.account_number = account_number;
        this.account_type = account_type;
        this.balance = balance;
        this.cards = cards;
        this.transactions = transactions;
        this.id = id;
    }

    public AccountResponseDTO() {
    }

    public List<CardResponseDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardResponseDTO> cards) {
        this.cards = cards;
    }

    public List<TransactionResponseDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponseDTO> transactions) {
        this.transactions = transactions;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
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
