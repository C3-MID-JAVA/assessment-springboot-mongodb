package co.com.sofka.cuentabancaria.dto.cuenta;

import co.com.sofka.cuentabancaria.model.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CuentaResponseDTO {

    private Long id;

    private String numeroCuenta;

    private double saldo;

    private String titular;

    public CuentaResponseDTO(Cuenta cuenta) {
        this.id = cuenta.getId();
        this.numeroCuenta = cuenta.getNumeroCuenta();
        this.saldo = cuenta.getSaldo();
        this.titular = cuenta.getTitular();
    }

}
