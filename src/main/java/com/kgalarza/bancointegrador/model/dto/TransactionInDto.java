package com.kgalarza.bancointegrador.model.dto;

import jakarta.validation.constraints.*;

public class TransactionInDto {

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El ID de cuenta no puede ser nulo")
    private String cuentaId;

    public TransactionInDto() {
    }

    public TransactionInDto(String descripcion, Double monto, String cuentaId) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.cuentaId = cuentaId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }

}
