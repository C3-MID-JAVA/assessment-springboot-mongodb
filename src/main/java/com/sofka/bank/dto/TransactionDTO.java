package com.sofka.bank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;

    @NotNull
    private String transactionType;

    @Positive
    private double amount;

    private double fee;
    private LocalDateTime date;

    private String description;


    private BankAccountDTO bankAccount;

    public TransactionDTO(Long id, String transactionType, double amount, double fee, LocalDateTime date, String description, BankAccountDTO bankAccountDTO) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.description = description;
        this.bankAccount = bankAccountDTO;
    }

    public void validar() {
        if (!transactionType.matches("DEPOSIT_ATM|DEPOSIT_OTHER_ACCOUNT|WITHDRAW_ATM|ONLINE_PURCHASE" +
                "BRANCH_DEPOSIT|POS_CARD_PURCHASE")) {
            throw new IllegalArgumentException("Tipo de transacción no válido.");
        }
    }

    // Getters and Setters
    public Long getId() {return id;}

    public String getTransactionType() {
        return transactionType;
    }
    public double getAmount(){
        return amount;
    }

    public double getFee(){
        return fee;
    }

    public LocalDateTime getDate(){return date;}

    public String getDescription(){
        return description;
    }

    public BankAccountDTO getBankAccount() {
        return bankAccount;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFee(double fee){
        this.fee = fee;
    }

    public void setDate(LocalDateTime date){
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBankAccount(BankAccountDTO bankAccount) {
        this.bankAccount = bankAccount;
    }
}