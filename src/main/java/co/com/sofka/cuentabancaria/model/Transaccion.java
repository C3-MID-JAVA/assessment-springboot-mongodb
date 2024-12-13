package co.com.sofka.cuentabancaria.model;

import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double monto;

    @Column(nullable = false)
    private double costoTransaccion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id")
    @JsonIgnore
    private Cuenta cuenta;

    public Transaccion(double monto, double costoTransaccion, LocalDateTime fecha, TipoTransaccion tipo, Cuenta cuenta) {
        this.monto = monto;
        this.costoTransaccion = costoTransaccion;
        this.fecha = fecha;
        this.tipo = tipo;
        this.cuenta = cuenta;
    }

}
