package com.kgalarza.bancointegrador.model.dto;

import java.util.List;

public class CuentaOutDto {
    private Long id;
    private String numeroCuenta;
    private Double saldo;
    private Long clienteId;
    private List<Long> movimientosIds;
    private Long tarjetaId;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Long> getMovimientosIds() {
        return movimientosIds;
    }

    public void setMovimientosIds(List<Long> movimientosIds) {
        this.movimientosIds = movimientosIds;
    }

    public Long getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(Long tarjetaId) {
        this.tarjetaId = tarjetaId;
    }
}
