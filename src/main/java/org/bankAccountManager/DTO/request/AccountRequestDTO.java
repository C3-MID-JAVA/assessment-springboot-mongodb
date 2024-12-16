package org.bankAccountManager.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class AccountRequestDTO {
    private int id;
    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{10,20}$", message = "Account number must be alphanumeric and between 10 and 20 characters")
    private String account_number;
    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "^(savings|current|fixed|credit)$", message = "Account type must be one of the following: savings, current, fixed, credit")
    private String account_type;
    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance must not be negative")
    private BigDecimal balance;
    @NotNull(message = "Customer is required")
    @Valid
    private CustomerRequestDTO customer;

    public AccountRequestDTO(String account_number, String account_type, BigDecimal balance, CustomerRequestDTO customer, int id) {
        this.account_number = account_number;
        this.account_type = account_type;
        this.balance = balance;
        this.customer = customer;
        this.id = id;
    }

    public AccountRequestDTO() {
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

    public CustomerRequestDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerRequestDTO customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
