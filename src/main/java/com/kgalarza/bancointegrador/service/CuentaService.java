package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.CuentaInDto;
import com.kgalarza.bancointegrador.model.dto.CuentaOutDto;

import java.util.List;

public interface CuentaService {

    CuentaOutDto crearCuenta(CuentaInDto cuentaInDto);

    List<CuentaOutDto> obtenerTodasLasCuentas();

    CuentaOutDto obtenerCuentaPorId(Long id);

    CuentaOutDto actualizarCuenta(Long id, CuentaInDto cuentaInDto);

    void eliminarCuenta(Long id);
}
