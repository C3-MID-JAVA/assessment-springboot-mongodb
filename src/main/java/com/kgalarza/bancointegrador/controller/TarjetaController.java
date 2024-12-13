package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.TarjetaInDto;
import com.kgalarza.bancointegrador.model.dto.TarjetaOutDto;
import com.kgalarza.bancointegrador.service.TarjetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/tarjetas")
public class TarjetaController {

    private final TarjetaService tarjetaService;

    @Autowired
    public TarjetaController(TarjetaService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @PostMapping
    public ResponseEntity<TarjetaOutDto> crearTarjeta(@Valid @RequestBody TarjetaInDto tarjetaInDto) {
        TarjetaOutDto nuevaTarjeta = tarjetaService.crearTarjeta(tarjetaInDto);
        return ResponseEntity.ok(nuevaTarjeta);
    }

    @GetMapping
    public ResponseEntity<List<TarjetaOutDto>> obtenerTodasLasTarjetas() {
        List<TarjetaOutDto> tarjetas = tarjetaService.obtenerTodasLasTarjetas();
        return ResponseEntity.ok(tarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TarjetaOutDto> obtenerTarjetaPorId(@PathVariable Long id) {
        TarjetaOutDto tarjeta = tarjetaService.obtenerTarjetaPorId(id);
        return ResponseEntity.ok(tarjeta);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TarjetaOutDto> actualizarTarjeta(@PathVariable Long id, @Valid  @RequestBody TarjetaInDto tarjetaInDto) {
        TarjetaOutDto tarjetaActualizada = tarjetaService.actualizarTarjeta(id, tarjetaInDto);
        return ResponseEntity.ok(tarjetaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable Long id) {
            tarjetaService.eliminarTarjeta(id);
            return ResponseEntity.noContent().build();
    }
}
