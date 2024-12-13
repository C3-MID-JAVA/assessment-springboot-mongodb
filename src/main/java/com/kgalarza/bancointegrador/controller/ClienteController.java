package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.ClienteInDto;
import com.kgalarza.bancointegrador.model.dto.ClienteOutDto;
import com.kgalarza.bancointegrador.service.ClienteService;
import com.kgalarza.bancointegrador.service.impl.ClienteImplService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }



    @PostMapping
    public ResponseEntity<ClienteOutDto> crearCliente(@Valid @RequestBody ClienteInDto clienteInDto) {
        ClienteOutDto clienteGuardado = clienteService.guardarCliente(clienteInDto);
        return ResponseEntity.ok(clienteGuardado);
    }

    @GetMapping
    public List<ClienteOutDto> obtenerClientes() {
        return clienteService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteOutDto> obtenerClientePorId(@PathVariable Long id) {
        ClienteOutDto cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteOutDto> actualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteInDto clienteInDto) {
        ClienteOutDto clienteActualizado = clienteService.actualizarCliente(id, clienteInDto);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
