package com.kgalarza.bancointegrador.model.dto;

public class CardInDto {

    private String numeroTarjeta;
    private String tipo;
    private String fechaExpiracion;
    private String cuentaId;

    public CardInDto() {
    }

    public CardInDto(String numeroTarjeta, String tipo, String fechaExpiracion, String cuentaId) {
        this.numeroTarjeta = numeroTarjeta;
        this.tipo = tipo;
        this.fechaExpiracion = fechaExpiracion;
        this.cuentaId = cuentaId;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }
}
