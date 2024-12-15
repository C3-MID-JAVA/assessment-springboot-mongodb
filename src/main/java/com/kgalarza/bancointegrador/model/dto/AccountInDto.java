package com.kgalarza.bancointegrador.model.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class AccountInDto {

    @NotNull(message = "El número de cuenta no puede ser nulo")
    private String numeroCuenta;

    @NotNull(message = "El saldo no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    private BigDecimal saldo;

    @NotNull(message = "El ID de cliente no puede ser nulo")
    private String clienteId;

    public AccountInDto() {
    }

    public AccountInDto(String numeroCuenta, BigDecimal saldo, String clienteId) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.clienteId = clienteId;
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

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }
}
