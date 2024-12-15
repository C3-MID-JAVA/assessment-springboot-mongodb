package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.AccountMapper;
import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaImplService implements CuentaService {

    private final AccountRepository cuentaRepository;
    private final ClientRepository clienteRepository;

    @Autowired
    public CuentaImplService(AccountRepository cuentaRepository, ClientRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public AccountOutDto crearCuenta(AccountInDto cuentaInDto) {
        Client cliente = clienteRepository.findById(String.valueOf(cuentaInDto.getClienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cuentaInDto.getClienteId()));

        Account cuenta = AccountMapper.toEntity(cuentaInDto, cliente);

        Account cuentaGuardada = cuentaRepository.save(cuenta);

        return AccountMapper.toDto(cuentaGuardada);
    }


    @Override
    public List<AccountOutDto> obtenerTodasLasCuentas() {
        List<AccountOutDto> cuentas = cuentaRepository.findAll().stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());

        if (cuentas.isEmpty()) {
            throw new ResourceNotFoundException("No existen cuentas registradas.");
        }

        return cuentas;
    }
    @Override
    public AccountOutDto obtenerCuentaPorId(String id) {
        Account cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        return AccountMapper.toDto(cuenta);
    }

    @Override
    public AccountOutDto actualizarCuenta(String id, AccountInDto cuentaInDto) {
        Account cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        cuentaExistente.setNumeroCuenta(cuentaInDto.getNumeroCuenta());
        cuentaExistente.setSaldo(cuentaInDto.getSaldo());

//        if (cuentaInDto.getClienteId() != null) {
//            Cliente cliente = clienteRepository.findById(String.valueOf(cuentaInDto.getClienteId()))
//                    .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + cuentaInDto.getClienteId()));
//            cuentaExistente.setCliente(cliente);
//        }

        Account cuentaActualizada = cuentaRepository.save(cuentaExistente);

        return AccountMapper.toDto(cuentaActualizada);
    }

    @Override
    public void eliminarCuenta(String id) {
        Account cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        cuentaRepository.delete(cuenta);
    }
}
