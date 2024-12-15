package com.kgalarza.bancointegrador.model.dto;

public class CardOutDto {

    private String id;
    private String numeroTarjeta;
    private String tipo;
    private String cuentaId;

    public CardOutDto() {
    }

    public CardOutDto(String id, String cuentaId, String tipo, String numeroTarjeta) {
        this.id = id;
        this.cuentaId = cuentaId;
        this.tipo = tipo;
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCuentaId(String cuentaId) {
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

    public String getCuentaId() {
        return cuentaId;
    }
}
