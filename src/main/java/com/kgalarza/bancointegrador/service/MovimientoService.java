package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.MovimientoInDto;
import com.kgalarza.bancointegrador.model.dto.MovimientoOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovimientoService {

    MovimientoOutDto realizarDepositoSucursal(MovimientoInDto movimientoInDto);

    MovimientoOutDto realizarDepositoCajero(MovimientoInDto movimientoInDto);

    MovimientoOutDto realizarDepositoOtraCuenta(MovimientoInDto movimientoInDto);

    MovimientoOutDto realizarCompraFisica(MovimientoInDto movimientoInDto);

    MovimientoOutDto realizarCompraWeb(MovimientoInDto movimientoInDto);

    MovimientoOutDto realizarRetiroCajero(MovimientoInDto movimientoInDto);

//    List<MovimientoOutDto> obtenerMovimientosPorCuenta(Long cuentaId);
}
