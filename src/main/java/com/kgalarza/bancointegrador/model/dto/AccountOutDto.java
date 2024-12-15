package com.kgalarza.bancointegrador.model.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountOutDto {

    private String id;
    private String numeroCuenta;
    private BigDecimal saldo;

    public AccountOutDto() {
    }

    public AccountOutDto(String id, String numeroCuenta, BigDecimal saldo) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public BigDecimal  getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal  saldo) {
        this.saldo = saldo;
    }

}
