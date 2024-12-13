package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.TarjetaInDto;
import com.kgalarza.bancointegrador.model.dto.TarjetaOutDto;
import com.kgalarza.bancointegrador.model.entity.Cuenta;
import com.kgalarza.bancointegrador.model.entity.Tarjeta;
import org.springframework.stereotype.Component;

@Component
public class TarjetaMapper {

    public static Tarjeta mapToEntity(TarjetaInDto tarjetaInDto, Cuenta cuenta) {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setNumeroTarjeta(tarjetaInDto.getNumeroTarjeta());
        tarjeta.setTipo(tarjetaInDto.getTipo());
        tarjeta.setCuenta(cuenta);
        return tarjeta;
    }

    public static TarjetaOutDto mapToDto(Tarjeta tarjeta) {
        TarjetaOutDto dto = new TarjetaOutDto();
        dto.setId(tarjeta.getId());
        dto.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
        dto.setTipo(tarjeta.getTipo());
        dto.setCuentaId(tarjeta.getCuenta().getId());
        return dto;
    }
}
