package org.bankAccountManager.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;
    private Timestamp date;
    private String type;
    private BigDecimal amount;
    private String description;

    public Transaction(BigDecimal amount, Branch branch, Timestamp date, String description, Account destinationAccount, int id, Account sourceAccount, String type) {
        this.amount = amount;
        this.branch = branch;
        this.date = date;
        this.description = description;
        this.destinationAccount = destinationAccount;
        this.id = id;
        this.sourceAccount = sourceAccount;
        this.type = type;
    }

    public Transaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch_id(Branch branch) {
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

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destination_account) {
        this.destinationAccount = destination_account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account source_account) {
        this.sourceAccount = source_account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
