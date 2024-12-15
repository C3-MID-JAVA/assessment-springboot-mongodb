package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/clientes")
@Tag(name = "Clientes", description = "Endpoints para la gestión de clientes")
public class ClientController {

    private final ClienteService clienteService;

    public ClientController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @Operation(summary = "Crear un nuevo cliente",
            description = "Permite registrar un nuevo cliente en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<ClientOutDto> crearCliente(@Valid @RequestBody ClientInDto clienteInDto) {
        ClientOutDto clienteGuardado = clienteService.guardarCliente(clienteInDto);
        return ResponseEntity.ok(clienteGuardado);
    }

    @Operation(summary = "Obtener la lista de todos los clientes",
            description = "Devuelve un listado con la información de todos los clientes registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    })
    @GetMapping
    public List<ClientOutDto> obtenerClientes() {
        return clienteService.obtenerTodos();
    }

    @Operation(summary = "Obtener cliente por ID",
            description = "Permite obtener la información de un cliente en específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientOutDto> obtenerClientePorId(@PathVariable String id) {
        ClientOutDto cliente = clienteService.obtenerPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Actualizar un cliente existente",
            description = "Permite actualizar la información de un cliente específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientOutDto> actualizarCliente(@PathVariable String id, @Valid @RequestBody ClientInDto clienteInDto) {
        ClientOutDto clienteActualizado = clienteService.actualizarCliente(id, clienteInDto);
        return ResponseEntity.ok(clienteActualizado);
    }

    @Operation(summary = "Eliminar un cliente",
            description = "Permite eliminar un cliente específico mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
