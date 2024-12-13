package com.kgalarza.bancointegrador.model.dto;

import jakarta.validation.constraints.*;

public class CuentaInDto {

    @NotNull(message = "El número de cuenta no puede ser nulo")
    @Size(min = 10, max = 10, message = "El número de cuenta debe tener exactamente 10 caracteres")
    @Pattern(regexp = "\\d{10}", message = "El número de cuenta debe ser un número de 10 dígitos")
    private String numeroCuenta;

    @NotNull(message = "El saldo no puede ser nulo")
    @DecimalMin(value = "0.0", inclusive = true, message = "El saldo no puede ser negativo")
    private Double saldo;

    @NotNull(message = "El ID de cliente no puede ser nulo")
    @Positive(message = "El ID de cliente debe ser un número positivo")
    private Long clienteId;


    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
}
