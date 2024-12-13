package com.kgalarza.bancointegrador.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroTarjeta;
    private String tipo; // Crédito o Débito

    @OneToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}
