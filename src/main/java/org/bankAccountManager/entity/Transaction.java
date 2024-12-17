package org.bankAccountManager.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Document(collection = "transaction")
public class Transaction {
    @Id
    private int id;
    @DBRef
    private List<Branch> branches;
    private Timestamp date;
    private String type;
    private BigDecimal amount;
    private String description;
    private int destinationAccountId;
    private int sourceAccountId;

    public Transaction(BigDecimal amount, List<Branch> branches, Timestamp date, String description, int id, String type) {
        this.amount = amount;
        this.branches = branches;
        this.date = date;
        this.description = description;
        this.id = id;
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

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(int destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public int getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(int sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }
}
