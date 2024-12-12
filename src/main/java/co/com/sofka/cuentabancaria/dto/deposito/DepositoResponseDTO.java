package co.com.sofka.cuentabancaria.dto.deposito;

import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.model.Transaccion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepositoResponseDTO {

    private Long transaccionId;

    private Long cuentaId;

    private double monto;

    private double nuevoSaldo;

    public DepositoResponseDTO(Transaccion transaccion) {
        this.transaccionId = transaccion.getId();
        this.cuentaId = transaccion.getCuenta().getId();
        this.monto = transaccion.getMonto();
        this.nuevoSaldo = transaccion.getCuenta().getSaldo();
    }

}
