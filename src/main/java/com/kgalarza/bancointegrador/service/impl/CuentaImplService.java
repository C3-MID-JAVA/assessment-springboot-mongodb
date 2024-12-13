package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.mapper.CuentaMapper;
import com.kgalarza.bancointegrador.model.dto.CuentaInDto;
import com.kgalarza.bancointegrador.model.dto.CuentaOutDto;
import com.kgalarza.bancointegrador.model.entity.Cliente;
import com.kgalarza.bancointegrador.model.entity.Cuenta;
import com.kgalarza.bancointegrador.repository.ClienteRepository;
import com.kgalarza.bancointegrador.repository.CuentaRepository;
import com.kgalarza.bancointegrador.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CuentaImplService implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public CuentaImplService(CuentaRepository cuentaRepository, ClienteRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public CuentaOutDto crearCuenta(CuentaInDto cuentaInDto) {
        Cliente cliente = clienteRepository.findById(cuentaInDto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + cuentaInDto.getClienteId()));

        Cuenta cuenta = CuentaMapper.toEntity(cuentaInDto, cliente);

        Cuenta cuentaGuardada = cuentaRepository.save(cuenta);

        return CuentaMapper.toDto(cuentaGuardada);
    }

    @Override
    public List<CuentaOutDto> obtenerTodasLasCuentas() {
        return cuentaRepository.findAll().stream()
                .map(CuentaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CuentaOutDto obtenerCuentaPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        return CuentaMapper.toDto(cuenta);
    }

    @Override
    public CuentaOutDto actualizarCuenta(Long id, CuentaInDto cuentaInDto) {
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuentaExistente.setNumeroCuenta(cuentaInDto.getNumeroCuenta());
        cuentaExistente.setSaldo(cuentaInDto.getSaldo());

        if (cuentaInDto.getClienteId() != null) {
            Cliente cliente = clienteRepository.findById(cuentaInDto.getClienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + cuentaInDto.getClienteId()));
            cuentaExistente.setCliente(cliente);
        }

        Cuenta cuentaActualizada = cuentaRepository.save(cuentaExistente);

        return CuentaMapper.toDto(cuentaActualizada);
    }

    @Override
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuentaRepository.delete(cuenta);
    }
}
