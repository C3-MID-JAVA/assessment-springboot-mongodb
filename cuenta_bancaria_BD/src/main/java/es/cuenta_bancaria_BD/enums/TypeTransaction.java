package es.cuenta_bancaria_BD.enums;

import java.math.BigDecimal;

public enum TypeTransaction {
    DEPOSITO_SUCURSAL(BigDecimal.ZERO),
    DEPOSITO_CAJERO(BigDecimal.valueOf(2)),
    DEPOSITO_OTRA_CUENTA(BigDecimal.valueOf(1.5)),
    COMPRA_WEB(BigDecimal.valueOf(5)),
    RETIRO_CAJERO(BigDecimal.ONE),
    COMPRA_ESTABLECIMIENTO(BigDecimal.ZERO);

    private final BigDecimal costo;


    TypeTransaction(BigDecimal costo) {
        this.costo = costo;
    }


    public BigDecimal getCosto() {
        return costo;
    }

    public static TypeTransaction fromString(String tipo) {
        try {
            return TypeTransaction.valueOf(tipo);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
