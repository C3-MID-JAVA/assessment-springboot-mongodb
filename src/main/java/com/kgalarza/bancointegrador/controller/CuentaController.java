package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.CuentaInDto;
import com.kgalarza.bancointegrador.model.dto.CuentaOutDto;
import com.kgalarza.bancointegrador.service.CuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaOutDto> crearCuenta(@Valid @RequestBody CuentaInDto cuentaInDto) {
        CuentaOutDto nuevaCuenta = cuentaService.crearCuenta(cuentaInDto);
        return ResponseEntity.ok(nuevaCuenta);
    }

    @GetMapping
    public ResponseEntity<List<CuentaOutDto>> obtenerTodasLasCuentas() {
        List<CuentaOutDto> cuentas = cuentaService.obtenerTodasLasCuentas();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaOutDto> obtenerCuentaPorId(@PathVariable Long id) {
        CuentaOutDto cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaOutDto> actualizarCuenta(@PathVariable Long id, @Valid @RequestBody CuentaInDto cuentaInDto) {
        CuentaOutDto cuentaActualizada = cuentaService.actualizarCuenta(id, cuentaInDto);
        return ResponseEntity.ok(cuentaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}
