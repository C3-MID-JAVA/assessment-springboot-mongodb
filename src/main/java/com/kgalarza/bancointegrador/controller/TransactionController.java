package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/movimientos")
public class TransactionController {

    private final TransactionService movimientoService;

    @Autowired
    public TransactionController(TransactionService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping("/deposito/sucursal")
    public ResponseEntity<TransactionOutDto> realizarDepositoSucursal(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makeBranchDeposit(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/deposito/cajero")
    public ResponseEntity<TransactionOutDto> realizarDepositoCajero(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makeATMDeposit(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/deposito/otra-cuenta")
    public ResponseEntity<TransactionOutDto> realizarDepositoOtraCuenta(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makeDepositToAnotherAccount(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/compra/fisica")
    public ResponseEntity<TransactionOutDto> realizarCompraFisica(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makePhysicalPurchase(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/compra/web")
    public ResponseEntity<TransactionOutDto> realizarCompraWeb(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makeOnlinePurchase(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/retiro/cajero")
    public ResponseEntity<TransactionOutDto> realizarRetiroCajero(@Valid @RequestBody TransactionInDto movimientoInDto) {
        TransactionOutDto movimiento = movimientoService.makeATMWithdrawal(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

}
