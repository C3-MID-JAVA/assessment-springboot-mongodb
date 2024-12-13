package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.CuentaInDto;
import com.kgalarza.bancointegrador.model.dto.CuentaOutDto;
import com.kgalarza.bancointegrador.model.entity.Cliente;
import com.kgalarza.bancointegrador.model.entity.Cuenta;
import com.kgalarza.bancointegrador.model.entity.Movimiento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CuentaMapper {
    public static Cuenta toEntity(CuentaInDto dto, Cliente cliente) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setCliente(cliente);
        return cuenta;
    }

    public static CuentaOutDto toDto(Cuenta cuenta) {
        CuentaOutDto dto = new CuentaOutDto();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setSaldo(cuenta.getSaldo());
        dto.setClienteId(cuenta.getCliente().getId());

        if (cuenta.getMovimientos() != null) {
            List<Long> movimientosIds = cuenta.getMovimientos().stream()
                    .map(Movimiento::getId)
                    .collect(Collectors.toList());
            dto.setMovimientosIds(movimientosIds);
        }

        if (cuenta.getTarjeta() != null) {
            dto.setTarjetaId(cuenta.getTarjeta().getId());
        }

        return dto;
    }
}
