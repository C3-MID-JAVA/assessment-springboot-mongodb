package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Transaction;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.TransactionRepository;
import com.kgalarza.bancointegrador.service.MovimientoService;
import com.kgalarza.bancointegrador.util.CostosTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgalarza.bancointegrador.mapper.TransactionMapper;

@Service
public class MovimientoImplService implements MovimientoService {

    private final TransactionRepository movimientoRepository;
    private final AccountRepository cuentaRepository;

    @Autowired
    public MovimientoImplService(TransactionRepository movimientoRepository, AccountRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public TransactionOutDto realizarDepositoSucursal(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.DEPOSITO_SUCURSAL.getCosto());
    }

    @Override
    public TransactionOutDto realizarDepositoCajero(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto,  CostosTransaccion.DEPOSITO_CAJERO.getCosto());
    }

    @Override
    public TransactionOutDto realizarDepositoOtraCuenta(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.DEPOSITO_OTRA_CUENTA.getCosto());
    }

    @Override
    public TransactionOutDto realizarCompraFisica(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.COMPRA_FISICA.getCosto(), false);
    }

    @Override
    public TransactionOutDto realizarCompraWeb(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.COMPRA_WEB.getCosto(), false);
    }

    @Override
    public TransactionOutDto realizarRetiroCajero(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.RETIRO_CAJERO.getCosto(), false);
    }

    private TransactionOutDto realizarTransaccion(TransactionInDto movimientoInDto, double costoTransaccion) {
        return realizarTransaccion(movimientoInDto, costoTransaccion, true);
    }

    private TransactionOutDto realizarTransaccion(TransactionInDto movimientoInDto, double costoTransaccion, boolean esDeposito) {
        Account cuenta = cuentaRepository.findById(movimientoInDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + movimientoInDto.getCuentaId()));

        double saldoAfectado = esDeposito
                ? movimientoInDto.getMonto() - costoTransaccion
                : -movimientoInDto.getMonto() - costoTransaccion;

        if (cuenta.getSaldo() + saldoAfectado < 0) {
            throw new ResourceNotFoundException("Saldo insuficiente para realizar la transacción.");
        }

        cuenta.setSaldo(cuenta.getSaldo() + saldoAfectado);
        cuentaRepository.save(cuenta);

        Transaction movimiento = new Transaction();
        movimiento.setDescripcion(movimientoInDto.getDescripcion());
        //movimiento.setMonto(movimientoInDto.getMonto());
        movimiento.setMonto(saldoAfectado);
        movimiento.setTipoMovimiento(movimientoInDto.getTipoMovimiento());
        movimiento.setFecha(movimientoInDto.getFecha());
//        movimiento.setCuenta(cuenta);
        movimientoRepository.save(movimiento);

        return TransactionMapper.mapToDto(movimiento);
    }


}
