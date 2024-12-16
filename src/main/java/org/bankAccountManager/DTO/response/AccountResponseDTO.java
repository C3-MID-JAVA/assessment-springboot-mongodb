package org.bankAccountManager.DTO.response;

import java.math.BigDecimal;

public class AccountResponseDTO {
    private int id;
    private String account_number;
    private String account_type;
    private BigDecimal balance;
    private CustomerResponseDTO customer;

    public AccountResponseDTO(String account_number, String account_type, BigDecimal balance, CustomerResponseDTO customer, int id) {
        this.account_number = account_number;
        this.account_type = account_type;
        this.balance = balance;
        this.customer = customer;
        this.id = id;
    }

    public AccountResponseDTO() {
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

    public CustomerResponseDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerResponseDTO customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
