package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TarjetaService {

    CardOutDto crearTarjeta(CardInDto tarjetaInDto);

    List<CardOutDto> obtenerTodasLasTarjetas();

    CardOutDto obtenerTarjetaPorId(String id);

    CardOutDto actualizarTarjeta(String id, CardInDto tarjetaInDto);

    void eliminarTarjeta(String id);
}
