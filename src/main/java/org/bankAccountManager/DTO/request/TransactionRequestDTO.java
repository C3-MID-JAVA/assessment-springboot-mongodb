package org.bankAccountManager.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.bankAccountManager.entity.Branch;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionRequestDTO {

    private int id;
    @NotNull(message = "Branch is required")
    @Valid
    private List<BranchRequestDTO> branches;
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

    public TransactionRequestDTO(BigDecimal amount, List<BranchRequestDTO> branches, Timestamp date, String description,
                                 int id, String type) {
        this.amount = amount;
        this.branches = branches;
        this.date = date;
        this.description = description;
        this.id = id;
        this.type = type;
    }

    public TransactionRequestDTO() {
    }

    public @NotNull(message = "Transaction amount is required") @Positive(message = "Amount must be positive") BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Transaction amount is required") @Positive(message = "Amount must be positive") BigDecimal amount) {
        this.amount = amount;
    }

    public @NotNull(message = "Branch is required") @Valid List<BranchRequestDTO> getBranches() {
        return branches;
    }

    public void setBranches(@NotNull(message = "Branch is required") @Valid List<BranchRequestDTO> branches) {
        this.branches = branches;
    }

    public @NotNull(message = "Transaction date is required") Timestamp getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Transaction date is required") Timestamp date) {
        this.date = date;
    }

    public @Size(max = 255, message = "Description cannot exceed 255 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 255, message = "Description cannot exceed 255 characters") String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Transaction's type is required") @Pattern(regexp = "^(branch_transfer|another_account_deposit|store_card_purchase|online_card_purchase|atm_withdrawal|atm_deposit)$",
            message = "Invalid transaction type. Valid types are: branch_transfer, another_account_deposit, " +
                    "store_card_purchase, online_card_purchase, atm_withdrawal, atm_deposit") String getType() {
        return type;
    }

    public void setType(@NotNull(message = "Transaction's type is required") @Pattern(regexp = "^(branch_transfer|another_account_deposit|store_card_purchase|online_card_purchase|atm_withdrawal|atm_deposit)$",
            message = "Invalid transaction type. Valid types are: branch_transfer, another_account_deposit, " +
                    "store_card_purchase, online_card_purchase, atm_withdrawal, atm_deposit") String type) {
        this.type = type;
    }
}
