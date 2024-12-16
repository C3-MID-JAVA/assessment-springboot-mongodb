package org.bankAccountManager.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionRequestDTO {

    private int id;
    @NotNull(message = "Source account is required")
    @Valid
    private AccountRequestDTO source_account;
    @NotNull(message = "Destination account is required")
    @Valid
    private AccountRequestDTO destination_account;
    @NotNull(message = "Branch is required")
    @Valid
    private BranchRequestDTO branch;
    @NotNull(message = "Transaction date is required")
    private Timestamp date;
    @NotNull(message = "Transaction's type is required")
    @Pattern(regexp = "^(branch_transfer|another_account_deposit|store_card_purchase|online_card_purchase|atm_withdrawal|atm_deposit)$",
            message = "Invalid transaction type. Valid types are: branch_transfer, another_account_deposit, " +
                    "store_card_purchase, online_card_purchase, atm_withdrawal, atm_deposit")
    private String type;
    @NotNull(message = "Transaction amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    public TransactionRequestDTO(BigDecimal amount, BranchRequestDTO branch, Timestamp date, String description,
                                 AccountRequestDTO destination_account, int id, AccountRequestDTO source_account,
                                 String type) {
        this.amount = amount;
        this.branch = branch;
        this.date = date;
        this.description = description;
        this.destination_account = destination_account;
        this.id = id;
        this.source_account = source_account;
        this.type = type;
    }

    public TransactionRequestDTO() {}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BranchRequestDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchRequestDTO branch) {
        this.branch = branch;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AccountRequestDTO getDestination_account() {
        return destination_account;
    }

    public void setDestination_account(AccountRequestDTO destination_account) {
        this.destination_account = destination_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountRequestDTO getSource_account() {
        return source_account;
    }

    public void setSource_account(AccountRequestDTO source_account) {
        this.source_account = source_account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
