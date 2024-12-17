package org.bankAccountManager.DTO.response;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class TransactionResponseDTO {
    private int id;
    private List<BranchResponseDTO> branches;
    private Timestamp date;
    private String type;
    private BigDecimal amount;
    private String description;

    public TransactionResponseDTO(BigDecimal amount, List<BranchResponseDTO> branches, Timestamp date, String description, int id, String type) {
        this.amount = amount;
        this.branches = branches;
        this.date = date;
        this.description = description;
        this.id = id;
        this.type = type;
    }

    public TransactionResponseDTO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public List<BranchResponseDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchResponseDTO> branches) {
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

}
