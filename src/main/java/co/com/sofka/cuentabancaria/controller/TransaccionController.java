package co.com.sofka.cuentabancaria.controller;


import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.service.iservice.TransaccionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping("/depositos")
    public ResponseEntity <TransaccionResponseDTO> realizarDeposito(@RequestBody @Valid TransaccionRequestDTO depositoRequestDTO) {
        TransaccionResponseDTO deposito = transaccionService.realizarDeposito(depositoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(deposito);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TransaccionResponseDTO>> listarTransacciones() {
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerTransacciones();
        return ResponseEntity.status(HttpStatus.OK).body(transacciones);
    }

    @PostMapping("/retiro")
    public ResponseEntity <TransaccionResponseDTO> realizarRetiro(@RequestBody @Valid TransaccionRequestDTO transaccionRequestDTO) {
        TransaccionResponseDTO transaccion = transaccionService.realizarRetiro(transaccionRequestDTO);
        return ResponseEntity.ok(transaccion);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<TransaccionResponseDTO>> obtenerHistorialPorCuenta(@PathVariable Long cuentaId) {
        List<TransaccionResponseDTO> transaccionResponseDTO = transaccionService.obtenerHistorialPorCuenta(cuentaId);
        return ResponseEntity.ok(transaccionResponseDTO);
    }
}
