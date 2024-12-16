package com.kgalarza.bancointegrador.model.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "accounts")
public class Account {

    @Id
    private String id;
    private String numeroCuenta;
    private BigDecimal saldo;

    private String clienteId;
    private String tarjetaId;
    private List<String> movimientosIds;

    public Account() {
    }

    public Account(String id, String numeroCuenta, BigDecimal saldo, String clienteId, String tarjetaId) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.clienteId = clienteId;
        this.tarjetaId = tarjetaId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getTarjetaId() {
        return tarjetaId;
    }

    public void setTarjetaId(String tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    public List<String> getMovimientosIds() {
        return movimientosIds;
    }

    public void setMovimientosIds(List<String> movimientosIds) {
        this.movimientosIds = movimientosIds;
    }

    public BigDecimal  getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal  saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
