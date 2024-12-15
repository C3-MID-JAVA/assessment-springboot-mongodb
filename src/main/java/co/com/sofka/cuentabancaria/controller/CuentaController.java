package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.dto.util.PeticionByIdDTO;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @PostMapping("/listarById")
    public ResponseEntity<CuentaResponseDTO> obtenerCuentaPorId(@RequestBody PeticionByIdDTO cuentaRequestDTO) {
        CuentaResponseDTO cuenta = cuentaService.obtenerCuentaPorId(cuentaRequestDTO.getCuentaId());
        return ResponseEntity.status(HttpStatus.OK).body(cuenta);
    }


    @PostMapping("/listar/saldoById")
    public ResponseEntity<BigDecimal> consultarSaldo(@RequestBody PeticionByIdDTO cuentaRequestDTO) {
        BigDecimal saldo = cuentaService.consultarSaldo(cuentaRequestDTO.getCuentaId());
        return ResponseEntity.status(HttpStatus.OK).body(saldo);
    }


}