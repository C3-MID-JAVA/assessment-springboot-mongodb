package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.MovimientoInDto;
import com.kgalarza.bancointegrador.model.dto.MovimientoOutDto;
import com.kgalarza.bancointegrador.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    @Autowired
    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping("/deposito/sucursal")
    public ResponseEntity<MovimientoOutDto> realizarDepositoSucursal(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarDepositoSucursal(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/deposito/cajero")
    public ResponseEntity<MovimientoOutDto> realizarDepositoCajero(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarDepositoCajero(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/deposito/otra-cuenta")
    public ResponseEntity<MovimientoOutDto> realizarDepositoOtraCuenta(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarDepositoOtraCuenta(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/compra/fisica")
    public ResponseEntity<MovimientoOutDto> realizarCompraFisica(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarCompraFisica(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/compra/web")
    public ResponseEntity<MovimientoOutDto> realizarCompraWeb(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarCompraWeb(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

    @PostMapping("/retiro/cajero")
    public ResponseEntity<MovimientoOutDto> realizarRetiroCajero(@Valid @RequestBody MovimientoInDto movimientoInDto) {
        MovimientoOutDto movimiento = movimientoService.realizarRetiroCajero(movimientoInDto);
        return ResponseEntity.ok(movimiento);
    }

//    @GetMapping("/cuenta/{cuentaId}")
//    public ResponseEntity<List<MovimientoOutDto>> obtenerMovimientosPorCuenta(@PathVariable Long cuentaId) {
//        List<MovimientoOutDto> movimientos = movimientoService.obtenerMovimientosPorCuenta(cuentaId);
//        return ResponseEntity.ok(movimientos);
//    }
}
