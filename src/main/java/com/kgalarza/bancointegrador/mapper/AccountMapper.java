package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.model.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public static Account toEntity(AccountInDto dto, Client cliente) {
        Account cuenta = new Account();
        cuenta.setNumeroCuenta(dto.getNumeroCuenta());
        cuenta.setSaldo(dto.getSaldo());
//        cuenta.setCliente(cliente);
        return cuenta;
    }

    public static AccountOutDto toDto(Account cuenta) {
        AccountOutDto dto = new AccountOutDto();
        dto.setId(cuenta.getId());
        dto.setNumeroCuenta(cuenta.getNumeroCuenta());
        dto.setSaldo(cuenta.getSaldo());

        return dto;
    }
}
