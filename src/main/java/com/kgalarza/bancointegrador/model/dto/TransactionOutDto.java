package com.kgalarza.bancointegrador.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionOutDto {

    private String id;
    private String descripcion;
    private BigDecimal monto;
    private String tipoMovimiento;
    private LocalDate fecha;

    public TransactionOutDto() {
    }

    public TransactionOutDto(String id, String descripcion, BigDecimal monto, String tipoMovimiento, LocalDate fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }


    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
