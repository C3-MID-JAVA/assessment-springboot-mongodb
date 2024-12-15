package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.model.entity.Transaction;

public class TransactionMapper {

    public static TransactionOutDto mapToDto(Transaction movimiento) {
        TransactionOutDto dto = new TransactionOutDto();
        dto.setId(movimiento.getId());
        dto.setDescripcion(movimiento.getDescripcion());
        dto.setMonto(movimiento.getMonto());
        dto.setTipoMovimiento(movimiento.getTipoMovimiento());
        dto.setFecha(movimiento.getFecha());
//        dto.setCuentaId(movimiento.getCuenta().getId());
        return dto;
    }
}
