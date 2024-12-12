package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;


    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> crearCuenta(@Valid @RequestBody CuentaRequestDTO cuentaRequestDTO) {
        CuentaResponseDTO nuevaCuenta  = cuentaService.crearCuenta(cuentaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCuenta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorId(@PathVariable("id") Long id) {
        CuentaResponseDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<CuentaResponseDTO> consultarSaldo(@PathVariable Long id) {
        CuentaResponseDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

}




























