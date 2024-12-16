package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.AccountMapper;
import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountImplService implements AccountService {

    private final AccountRepository cuentaRepository;
    private final ClientRepository clienteRepository;

    @Autowired
    public AccountImplService(AccountRepository cuentaRepository, ClientRepository clienteRepository) {
        this.cuentaRepository = cuentaRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public AccountOutDto createAccount(AccountInDto cuentaInDto) {
        Client cliente = clienteRepository.findById(String.valueOf(cuentaInDto.getClienteId()))
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + cuentaInDto.getClienteId()));

        Account cuenta = AccountMapper.toEntity(cuentaInDto, cliente);

        Account cuentaGuardada = cuentaRepository.save(cuenta);

        return AccountMapper.toDto(cuentaGuardada);
    }


    @Override
    public List<AccountOutDto> getAllAccounts() {
        List<AccountOutDto> cuentas = cuentaRepository.findAll().stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());

        if (cuentas.isEmpty()) {
            throw new ResourceNotFoundException("No existen cuentas registradas.");
        }

        return cuentas;
    }
    @Override
    public AccountOutDto getAccountById(String id) {
        Account cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));
        return AccountMapper.toDto(cuenta);
    }

    @Override
    public AccountOutDto updateAccount(String id, AccountInDto cuentaInDto) {
        Account cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));

        cuentaExistente.setNumeroCuenta(cuentaInDto.getNumeroCuenta());
        cuentaExistente.setSaldo(cuentaInDto.getSaldo());

        return AccountMapper.toDto(cuentaRepository.save(cuentaExistente));
    }

    @Override
    public void deleteAccount(String id) {
        Account cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + id));
        cuentaRepository.delete(cuenta);
    }
}
