package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.RecursoNoEncontradoException;
import com.kgalarza.bancointegrador.model.dto.MovimientoInDto;
import com.kgalarza.bancointegrador.model.dto.MovimientoOutDto;
import com.kgalarza.bancointegrador.model.entity.Cuenta;
import com.kgalarza.bancointegrador.model.entity.Movimiento;
import com.kgalarza.bancointegrador.repository.CuentaRepository;
import com.kgalarza.bancointegrador.repository.MovimientoRepository;
import com.kgalarza.bancointegrador.service.MovimientoService;
import com.kgalarza.bancointegrador.util.CostosTransaccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kgalarza.bancointegrador.mapper.MovimientoMapper;

@Service
public class MovimientoImplService implements MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public MovimientoImplService(MovimientoRepository movimientoRepository, CuentaRepository cuentaRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public MovimientoOutDto realizarDepositoSucursal(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.DEPOSITO_SUCURSAL.getCosto());
    }

    @Override
    public MovimientoOutDto realizarDepositoCajero(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto,  CostosTransaccion.DEPOSITO_CAJERO.getCosto());
    }

    @Override
    public MovimientoOutDto realizarDepositoOtraCuenta(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.DEPOSITO_OTRA_CUENTA.getCosto());
    }

    @Override
    public MovimientoOutDto realizarCompraFisica(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.COMPRA_FISICA.getCosto(), false);
    }

    @Override
    public MovimientoOutDto realizarCompraWeb(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.COMPRA_WEB.getCosto(), false);
    }

    @Override
    public MovimientoOutDto realizarRetiroCajero(MovimientoInDto movimientoInDto) {
        return realizarTransaccion(movimientoInDto, CostosTransaccion.RETIRO_CAJERO.getCosto(), false);
    }

    private MovimientoOutDto realizarTransaccion(MovimientoInDto movimientoInDto, double costoTransaccion) {
        return realizarTransaccion(movimientoInDto, costoTransaccion, true);
    }

    private MovimientoOutDto realizarTransaccion(MovimientoInDto movimientoInDto, double costoTransaccion, boolean esDeposito) {
        Cuenta cuenta = cuentaRepository.findById(movimientoInDto.getCuentaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta no encontrada con ID: " + movimientoInDto.getCuentaId()));

        double saldoAfectado = esDeposito
                ? movimientoInDto.getMonto() - costoTransaccion
                : -movimientoInDto.getMonto() - costoTransaccion;

        if (cuenta.getSaldo() + saldoAfectado < 0) {
            throw new RecursoNoEncontradoException("Saldo insuficiente para realizar la transacción.");
        }

        cuenta.setSaldo(cuenta.getSaldo() + saldoAfectado);
        cuentaRepository.save(cuenta);

        Movimiento movimiento = new Movimiento();
        movimiento.setDescripcion(movimientoInDto.getDescripcion());
        //movimiento.setMonto(movimientoInDto.getMonto());
        movimiento.setMonto(saldoAfectado);
        movimiento.setTipoMovimiento(movimientoInDto.getTipoMovimiento());
        movimiento.setFecha(movimientoInDto.getFecha());
        movimiento.setCuenta(cuenta);
        movimientoRepository.save(movimiento);

        return MovimientoMapper.mapToDto(movimiento);
    }


}
