package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import com.kgalarza.bancointegrador.service.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/tarjetas")
public class CardController {

    private final CardService tarjetaService;

    @Autowired
    public CardController(CardService tarjetaService) {
        this.tarjetaService = tarjetaService;
    }

    @PostMapping
    public ResponseEntity<CardOutDto> crearTarjeta(@Valid @RequestBody CardInDto tarjetaInDto) {
        CardOutDto nuevaTarjeta = tarjetaService.crearTarjeta(tarjetaInDto);
        return ResponseEntity.ok(nuevaTarjeta);
    }

    @GetMapping
    public ResponseEntity<List<CardOutDto>> obtenerTodasLasTarjetas() {
        List<CardOutDto> tarjetas = tarjetaService.obtenerTodasLasTarjetas();
        return ResponseEntity.ok(tarjetas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardOutDto> obtenerTarjetaPorId(@PathVariable String id) {
        CardOutDto tarjeta = tarjetaService.obtenerTarjetaPorId(id);
        return ResponseEntity.ok(tarjeta);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CardOutDto> actualizarTarjeta(@PathVariable String id, @Valid  @RequestBody CardInDto tarjetaInDto) {
        CardOutDto tarjetaActualizada = tarjetaService.actualizarTarjeta(id, tarjetaInDto);
        return ResponseEntity.ok(tarjetaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarjeta(@PathVariable String id) {
            tarjetaService.eliminarTarjeta(id);
            return ResponseEntity.noContent().build();
    }
}
