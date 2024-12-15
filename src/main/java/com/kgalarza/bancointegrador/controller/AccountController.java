package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/cuentas")
public class AccountController {

    private final CuentaService cuentaService;

    @Autowired
    public AccountController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<AccountOutDto> crearCuenta(@Valid @RequestBody AccountInDto cuentaInDto) {
        AccountOutDto nuevaCuenta = cuentaService.crearCuenta(cuentaInDto);
        return ResponseEntity.ok(nuevaCuenta);
    }

    @GetMapping
    public ResponseEntity<List<AccountOutDto>> obtenerTodasLasCuentas() {
        List<AccountOutDto> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountOutDto> obtenerCuentaPorId(@PathVariable String id) {
        AccountOutDto cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountOutDto> actualizarCuenta(@PathVariable String id, @Valid @RequestBody AccountInDto cuentaInDto) {
        AccountOutDto cuentaActualizada = cuentaService.actualizarCuenta(id, cuentaInDto);
        return ResponseEntity.ok(cuentaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable String id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
