package com.sofka.bank.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;


public class BankAccountDTO {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountHolder;

    @Positive
    private double globalBalance;


    private List<TransactionDTO> transactions;

    public BankAccountDTO(Long id, String accountNumber, String accountHolder, double globalBalance,
                          List<TransactionDTO> transactions) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.globalBalance = globalBalance;
        this.transactions = new ArrayList<>();
    }

    //Getters y Setters
    public Long getId(){
        return id;
    }

    public void setId (Long id){
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
   public void setAccountHolder(String accountHolder){
        this.accountHolder = accountHolder;
   }

   public Double getGlobalBalance() { return globalBalance;}
    public void setGlobalBalance(double globalBalance){
        this.globalBalance = globalBalance;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> vehicles) {
        this.transactions = vehicles;
    }

}