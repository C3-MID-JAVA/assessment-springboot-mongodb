package org.bankAccountManager.DTO.response;

import java.sql.Timestamp;

public class CardResponseDTO {
    private int id;
    private AccountResponseDTO account;
    private String card_number;
    private String card_type;
    private Timestamp expiration_date;
    private String cvv;

    public CardResponseDTO(AccountResponseDTO account, String card_number, String card_type, String cvv, Timestamp expiration_date, int id) {
        this.account = account;
        this.card_number = card_number;
        this.card_type = card_type;
        this.cvv = cvv;
        this.expiration_date = expiration_date;
        this.id = id;
    }

    public CardResponseDTO() {
    }

    public AccountResponseDTO getAccount() {
        return account;
    }

    public void setAccount(AccountResponseDTO account) {
        this.account = account;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
