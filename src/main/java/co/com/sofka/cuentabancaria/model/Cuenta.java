package co.com.sofka.cuentabancaria.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Document(collection = "cuentas")
@Getter
@Setter
public class Cuenta {
    @Id
    private String id;

    private String numeroCuenta;

    private BigDecimal saldo;

    private String titular;

    private List<Transaccion> transacciones= new ArrayList<>();

}
