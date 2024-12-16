package org.bankAccountManager.DTO.response;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransactionResponseDTO {
    private int id;
    private AccountResponseDTO source_account;
    private AccountResponseDTO destination_account;
    private BranchResponseDTO branch;
    private Timestamp date;
    private String type;
    private BigDecimal amount;
    private String description;

    public TransactionResponseDTO(BigDecimal amount, BranchResponseDTO branch, Timestamp date, String description, AccountResponseDTO destination_account, int id, AccountResponseDTO source_account, String type) {
        this.amount = amount;
        this.branch = branch;
        this.date = date;
        this.description = description;
        this.destination_account = destination_account;
        this.id = id;
        this.source_account = source_account;
        this.type = type;
    }

    public TransactionResponseDTO() {}

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BranchResponseDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchResponseDTO branch) {
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

    public AccountResponseDTO getDestination_account() {
        return destination_account;
    }

    public void setDestination_account(AccountResponseDTO destination_account) {
        this.destination_account = destination_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AccountResponseDTO getSource_account() {
        return source_account;
    }

    public void setSource_account(AccountResponseDTO source_account) {
        this.source_account = source_account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
