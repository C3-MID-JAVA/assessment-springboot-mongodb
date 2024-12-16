package com.example.bank.dto;

import com.example.bank.utils.operations.OperationTypesEnum;

import java.math.BigDecimal;
import java.util.UUID;

public class OperationDTO {
    private String id;

    private BigDecimal value;

    private OperationTypesEnum type;

    private String accountId;

    public OperationDTO() {}

    public OperationDTO(BigDecimal value, OperationTypesEnum type, String accountId) {
        this.id = UUID.randomUUID().toString().substring(0,8);
        this.value = value;
        this.type = type;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public OperationTypesEnum getType() {
        return type;
    }

    public void setType(OperationTypesEnum type) {
        this.type = type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
