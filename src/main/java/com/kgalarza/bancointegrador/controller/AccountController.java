package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/cuentas")
public class AccountController {

    private final AccountService cuentaService;

    @Autowired
    public AccountController(AccountService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<AccountOutDto> createAccount(@Valid @RequestBody AccountInDto cuentaInDto) {
        AccountOutDto nuevaCuenta = cuentaService.createAccount(cuentaInDto);
        return ResponseEntity.ok(nuevaCuenta);
    }

    @GetMapping
    public ResponseEntity<List<AccountOutDto>> getAllAccounts() {
        List<AccountOutDto> cuentas = cuentaService.getAllAccounts();
        return ResponseEntity.ok(cuentas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountOutDto> getAccountById(@PathVariable String id) {
        AccountOutDto cuenta = cuentaService.getAccountById(id);
        return ResponseEntity.ok(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountOutDto> updateAccount(@PathVariable String id, @Valid @RequestBody AccountInDto cuentaInDto) {
        AccountOutDto cuentaActualizada = cuentaService.updateAccount(id, cuentaInDto);
        return ResponseEntity.ok(cuentaActualizada);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        cuentaService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
