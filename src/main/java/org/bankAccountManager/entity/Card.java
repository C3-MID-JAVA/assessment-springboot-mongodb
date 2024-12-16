package org.bankAccountManager.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY) // Propaga las operaciones persistentes
    @JoinColumn(name = "account_id")
    private Account account;
    private String cardNumber;
    private String cardType;
    private Timestamp expirationDate;
    private String cvv;

    public Card(Account account, String cardNumber, String cardType, Timestamp expirationDate, int id) {
        this.account = account;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.id = id;
    }

    public Card() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String card_number) {
        this.cardNumber = card_number;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String card_type) {
        this.cardType = card_type;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expiration_date) {
        this.expirationDate = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
