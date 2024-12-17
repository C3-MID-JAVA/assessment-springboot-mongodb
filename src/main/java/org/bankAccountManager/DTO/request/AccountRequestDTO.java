package org.bankAccountManager.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.bankAccountManager.entity.Card;
import org.bankAccountManager.entity.Transaction;

import java.math.BigDecimal;
import java.util.List;

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
    @NotNull(message = "Card is required")
    @Valid
    private List<CardRequestDTO> cards;
    @NotNull(message = "Transaction is required")
    @Valid
    private List<TransactionRequestDTO> transactions;

    public AccountRequestDTO(String account_number, String account_type, BigDecimal balance, List<CardRequestDTO> cards, List<TransactionRequestDTO> transactions, int id) {
        this.account_number = account_number;
        this.account_type = account_type;
        this.balance = balance;
        this.cards = cards;
        this.transactions = transactions;
        this.id = id;
    }

    public AccountRequestDTO() {
    }

    public @NotBlank(message = "Account number is required") @Pattern(regexp = "^[a-zA-Z0-9]{10,20}$", message = "Account number must be alphanumeric and between 10 and 20 characters") String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(@NotBlank(message = "Account number is required") @Pattern(regexp = "^[a-zA-Z0-9]{10,20}$", message = "Account number must be alphanumeric and between 10 and 20 characters") String account_number) {
        this.account_number = account_number;
    }

    public @NotBlank(message = "Account type is required") @Pattern(regexp = "^(savings|current|fixed|credit)$", message = "Account type must be one of the following: savings, current, fixed, credit") String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(@NotBlank(message = "Account type is required") @Pattern(regexp = "^(savings|current|fixed|credit)$", message = "Account type must be one of the following: savings, current, fixed, credit") String account_type) {
        this.account_type = account_type;
    }

    public @NotNull(message = "Balance is required") @Min(value = 0, message = "Balance must not be negative") BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(@NotNull(message = "Balance is required") @Min(value = 0, message = "Balance must not be negative") BigDecimal balance) {
        this.balance = balance;
    }

    public @NotNull(message = "Card is required") @Valid List<CardRequestDTO> getCards() {
        return cards;
    }

    public void setCards(@NotNull(message = "Card is required") @Valid List<CardRequestDTO> cards) {
        this.cards = cards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Transaction is required") @Valid List<TransactionRequestDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(@NotNull(message = "Transaction is required") @Valid List<TransactionRequestDTO> transactions) {
        this.transactions = transactions;
    }
}
