package co.com.sofka.cuentabancaria.dto.transaccion;

import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransaccionRequestDTO {

    @NotNull
    private String cuentaId;

    @NotNull
    @Positive(message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotNull
    private TipoTransaccion tipoTransaccion;

}
