package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Card;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {

    public static Card mapToEntity(CardInDto tarjetaInDto, Account cuenta) {
        Card tarjeta = new Card();
        tarjeta.setNumeroTarjeta(tarjetaInDto.getNumeroTarjeta());
        tarjeta.setTipo(tarjetaInDto.getTipo());
        tarjeta.setCuenta(cuenta);
        return tarjeta;
    }

    public static CardOutDto mapToDto(Card tarjeta) {
        CardOutDto dto = new CardOutDto();
        dto.setId(tarjeta.getId());
        dto.setNumeroTarjeta(tarjeta.getNumeroTarjeta());
        dto.setTipo(tarjeta.getTipo());
        dto.setCuentaId(tarjeta.getCuenta().getId());
        return dto;
    }
}
