package es.cuenta_bancaria_BD.controllers;


import es.cuenta_bancaria_BD.dto.AccountDTO;
import es.cuenta_bancaria_BD.service.IAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Tag(name = "Account Management", description = "Endpoints for managing bank accounts")
@RestController
@RequestMapping("/api/cuentas")
public class AccountController {
    @Autowired
    private IAccountService cuentaServicio;

    @Operation(summary = "List Accounts", description = "List of accounts created.")
    @GetMapping
    public ResponseEntity<List<AccountDTO>> listAccount(){
        var response = cuentaServicio.listarCuentas();
        return response.isEmpty()?
                ResponseEntity.noContent().build():
                ResponseEntity.status(200).body(response);
    }
    @Operation(summary = "Get Account by ID", description = "Fetches an account using its ID")
    @GetMapping("{id}")
    public ResponseEntity<AccountDTO> searchAccountById(@PathVariable String id){
        var cuenta = cuentaServicio.obtenerCuentaPorId(id);
        return cuenta != null ?
                ResponseEntity.ok(cuenta):
                ResponseEntity.noContent().build();
    }

    @Operation(summary = "Create Account", description = "Create an account.")
    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO cuentaDTO){
        System.out.println("Titular: " + cuentaDTO.getTitular());
        System.out.println("Saldo: " + cuentaDTO.getSaldo());
        var response = cuentaServicio.crearCuenta(cuentaDTO);

        return response != null?
                ResponseEntity.status(201).body(response):
                ResponseEntity.status(304).build();
    }

    @Operation(summary = "Make Transaccion", description = "Make a transaction: Deposit, withdrawal or purchase..")
    @PostMapping("{id}/transacciones")
    public ResponseEntity<AccountDTO> makeTransaction(@Valid
                                                      @PathVariable String id,
                                                      @RequestParam BigDecimal monto,
                                                      @RequestParam String tipo){
        var response = cuentaServicio.realizarTransaccion(id,monto,tipo);
        return ResponseEntity.ok(response);

    }

}
