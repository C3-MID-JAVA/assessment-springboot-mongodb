package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listar")
    public ResponseEntity<List<CuentaResponseDTO>> obtenerCuentas() {
        List<CuentaResponseDTO> cuentaResponseDTO = cuentaService.obtenerCuentas();

        return ResponseEntity.status(HttpStatus.OK).body(cuentaResponseDTO);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorId(@PathVariable("id") Long id) {
        CuentaResponseDTO cuenta = cuentaService.obtenerCuentaPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

    @GetMapping("/listar/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable Long id) {
        double cuenta = cuentaService.consultarSaldo(id);
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }

}