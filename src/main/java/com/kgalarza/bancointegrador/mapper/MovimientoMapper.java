package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.MovimientoOutDto;
import com.kgalarza.bancointegrador.model.entity.Movimiento;

public class MovimientoMapper {

    public static MovimientoOutDto mapToDto(Movimiento movimiento) {
        MovimientoOutDto dto = new MovimientoOutDto();
        dto.setId(movimiento.getId());
        dto.setDescripcion(movimiento.getDescripcion());
        dto.setMonto(movimiento.getMonto());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setFecha(movimiento.getFecha());
        dto.setCuentaId(movimiento.getCuenta().getId());
        return dto;
    }
}
