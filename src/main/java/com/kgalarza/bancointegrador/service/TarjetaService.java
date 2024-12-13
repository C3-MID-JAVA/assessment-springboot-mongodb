package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.TarjetaInDto;
import com.kgalarza.bancointegrador.model.dto.TarjetaOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TarjetaService {
    TarjetaOutDto crearTarjeta(TarjetaInDto tarjetaInDto);

    List<TarjetaOutDto> obtenerTodasLasTarjetas();

    TarjetaOutDto obtenerTarjetaPorId(Long id);

    TarjetaOutDto actualizarTarjeta(Long id, TarjetaInDto tarjetaInDto);

    void eliminarTarjeta(Long id);
}
