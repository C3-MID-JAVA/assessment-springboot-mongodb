package co.com.sofka.cuentabancaria.dto.transaccion;

import co.com.sofka.cuentabancaria.model.Transaccion;
import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransaccionResponseDTO {

    private Long id;

    private Long cuentaId;

    private TipoTransaccion tipoTransaccion;

    private double monto;

    private double costoTransaccion;

    private double nuevoSaldo;

    private LocalDateTime fecha;

    public TransaccionResponseDTO(Transaccion transaccion) {
        this.id = transaccion.getId();
        this.cuentaId = transaccion.getCuenta().getId();
        this.tipoTransaccion = transaccion.getTipo();
        this.monto = transaccion.getMonto();
        this.costoTransaccion = transaccion.getCostoTransaccion();
        this.fecha = transaccion.getFecha();
        this.nuevoSaldo = transaccion.getCuenta().getSaldo();
    }


}
