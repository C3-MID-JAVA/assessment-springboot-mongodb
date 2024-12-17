package org.bankAccountManager.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(collection = "card")
public class Card {
    @Id
    private int id;
    private String cardNumber;
    private String cardType;
    private Timestamp expirationDate;
    private String cvv;

    public Card(String cardNumber, String cardType, Timestamp expirationDate, int id) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.expirationDate = expirationDate;
        this.id = id;
    }

    public Card() {
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
