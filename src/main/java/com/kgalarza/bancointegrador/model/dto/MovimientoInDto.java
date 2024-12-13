package com.kgalarza.bancointegrador.model.dto;

import jakarta.validation.constraints.*;

public class MovimientoInDto {

    @NotNull(message = "La descripción no puede ser nula")
    @Size(min = 5, max = 255, message = "La descripción debe tener entre 5 y 255 caracteres")
    private String descripcion;

    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El tipo de movimiento no puede ser nulo")
    private String tipoMovimiento;

    @NotNull(message = "El ID de cuenta no puede ser nulo")
    @Positive(message = "El ID de cuenta debe ser un número positivo")
    private Long cuentaId;

    @NotNull(message = "La fecha no puede ser nula")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe estar en el formato 'yyyy-MM-dd'")
    private String fecha;
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

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Long getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(Long cuentaId) {
        this.cuentaId = cuentaId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
