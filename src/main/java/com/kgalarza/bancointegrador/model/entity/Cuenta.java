package com.kgalarza.bancointegrador.model.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cuentas")
public class Cuenta {

    @Id
    private long id;
    private String numeroCuenta;
    private Double saldo;

    private String clienteId;
    private String tarjetaId;
    private List<String> movimientosIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
