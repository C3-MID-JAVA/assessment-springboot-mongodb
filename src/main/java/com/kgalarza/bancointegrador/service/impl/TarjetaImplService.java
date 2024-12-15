package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.CardMapper;
import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Card;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.CardRepository;
import com.kgalarza.bancointegrador.service.TarjetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Service
public class TarjetaImplService implements TarjetaService {

    private final CardRepository tarjetaRepository;
    private final AccountRepository cuentaRepository;


    @Autowired
    public TarjetaImplService(CardRepository tarjetaRepository,
                              AccountRepository cuentaRepository,
                              CardMapper tarjetaMapper) {
        this.tarjetaRepository = tarjetaRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public CardOutDto crearTarjeta(CardInDto tarjetaInDto) {
        Account cuenta = cuentaRepository.findById(tarjetaInDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + tarjetaInDto.getCuentaId()));

        Card tarjeta = CardMapper.mapToEntity(tarjetaInDto, cuenta);
        Card tarjetaGuardada = tarjetaRepository.save(tarjeta);
        return CardMapper.mapToDto(tarjetaGuardada);
    }

    @Override
    public List<CardOutDto> obtenerTodasLasTarjetas() {
        List<CardOutDto> listTarjetas = tarjetaRepository.findAll().stream()
                .map(CardMapper::mapToDto)
                .collect(Collectors.toList());

        if (listTarjetas.isEmpty()) {
            throw new ResourceNotFoundException("No existen tarjetas registradas.");
        }
        return listTarjetas;
    }

    @Override
    public CardOutDto obtenerTarjetaPorId(String id) {
        Card tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarjeta no encontrada con ID: " + id));
        return CardMapper.mapToDto(tarjeta);
    }

    @Override
    public CardOutDto actualizarTarjeta(String id, CardInDto tarjetaInDto) {
        Card tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarjeta no encontrada con ID: " + id));

        Account cuenta = cuentaRepository.findById(tarjetaInDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + tarjetaInDto.getCuentaId()));

        tarjeta.setNumeroTarjeta(tarjetaInDto.getNumeroTarjeta());
        tarjeta.setTipo(tarjetaInDto.getTipo());
        tarjeta.setCuenta(cuenta);

        Card tarjetaActualizada = tarjetaRepository.save(tarjeta);
        return CardMapper.mapToDto(tarjetaActualizada);
    }

    @Override
    public void eliminarTarjeta(String id) {
        if (!tarjetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tarjeta no encontrada con ID: " + id);
        }
        tarjetaRepository.deleteById(id);
    }
}
