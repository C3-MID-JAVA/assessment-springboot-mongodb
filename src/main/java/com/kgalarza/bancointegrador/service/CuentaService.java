package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CuentaService {

    AccountOutDto crearCuenta(AccountInDto cuentaInDto);

    List<AccountOutDto> obtenerTodasLasCuentas();

    AccountOutDto obtenerCuentaPorId(String id);

    AccountOutDto actualizarCuenta(String id, AccountInDto cuentaInDto);

    void eliminarCuenta(String id);
}
