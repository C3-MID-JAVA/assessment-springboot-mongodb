package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Transaction;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.TransactionRepository;
import com.kgalarza.bancointegrador.service.TransactionService;
import com.kgalarza.bancointegrador.util.TransactionCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgalarza.bancointegrador.mapper.TransactionMapper;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransactionImplService implements TransactionService {

    private final TransactionRepository movimientoRepository;
    private final AccountRepository cuentaRepository;

    @Autowired
    public TransactionImplService(TransactionRepository movimientoRepository, AccountRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public TransactionOutDto makeBranchDeposit(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, TransactionCost.DEPOSITO_SUCURSAL.getCosto());
    }

    @Override
    public TransactionOutDto makeATMDeposit(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto,  TransactionCost.DEPOSITO_CAJERO.getCosto());
    }

    @Override
    public TransactionOutDto makeDepositToAnotherAccount(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, TransactionCost.DEPOSITO_OTRA_CUENTA.getCosto());
    }

    @Override
    public TransactionOutDto makePhysicalPurchase(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, TransactionCost.COMPRA_FISICA.getCosto(), false);
    }

    @Override
    public TransactionOutDto makeOnlinePurchase(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, TransactionCost.COMPRA_WEB.getCosto(), false);
    }

    @Override
    public TransactionOutDto makeATMWithdrawal(TransactionInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, TransactionCost.RETIRO_CAJERO.getCosto(), false);
    }

    private TransactionOutDto realizarTransaccion(TransactionInDto movimientoInDto, double costoTransaccion) {
        return realizarTransaccion(movimientoInDto, costoTransaccion, true);
    }

    private TransactionOutDto realizarTransaccion(TransactionInDto movimientoInDto, double costoTransaccion, boolean esDeposito) {
        Account cuenta = cuentaRepository.findById(movimientoInDto.getCuentaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada con ID: " + movimientoInDto.getCuentaId()));

        BigDecimal monto = new BigDecimal(movimientoInDto.getMonto());
        BigDecimal costoTransaccionBD = new BigDecimal(costoTransaccion);

        BigDecimal saldoAfectado = esDeposito
                ? monto.subtract(costoTransaccionBD)
                : monto.negate().subtract(costoTransaccionBD);


        if (cuenta.getSaldo().add(saldoAfectado).compareTo(BigDecimal.ZERO) < 0) {
            throw new ResourceNotFoundException("Saldo insuficiente para realizar la transacción.");
        }

        cuenta.setSaldo(cuenta.getSaldo().add(saldoAfectado));

        cuentaRepository.save(cuenta);

        Transaction movimiento = new Transaction();
        movimiento.setDescripcion(movimientoInDto.getDescripcion());
        movimiento.setMonto(saldoAfectado.abs());
        String tipoMovimiento = saldoAfectado.compareTo(BigDecimal.ZERO) < 0 ? "débito" : "crédito";
        movimiento.setTipoMovimiento(tipoMovimiento);
        movimiento.setFecha(LocalDate.now());

        movimientoRepository.save(movimiento);

        return TransactionMapper.mapToDto(movimiento);
    }


}
