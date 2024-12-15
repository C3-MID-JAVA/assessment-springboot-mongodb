package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import org.springframework.stereotype.Service;

@Service
public interface MovimientoService {

    TransactionOutDto realizarDepositoSucursal(TransactionInDto movimientoInDto);

    TransactionOutDto realizarDepositoCajero(TransactionInDto movimientoInDto);

    TransactionOutDto realizarDepositoOtraCuenta(TransactionInDto movimientoInDto);

    TransactionOutDto realizarCompraFisica(TransactionInDto movimientoInDto);

    TransactionOutDto realizarCompraWeb(TransactionInDto movimientoInDto);

    TransactionOutDto realizarRetiroCajero(TransactionInDto movimientoInDto);

}
