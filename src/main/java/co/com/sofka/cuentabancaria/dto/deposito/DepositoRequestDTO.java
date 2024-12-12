package co.com.sofka.cuentabancaria.dto.deposito;

import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositoRequestDTO {

    @NotNull
    private Long cuentaId;

    @NotNull
    @Positive(message = "el monto debe ser mayor a cero")
    private double monto;

    @NotNull
    private TipoTransaccion tipoTransaccion;
}
