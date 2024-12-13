package com.sofka.bank.controller;

import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.service.BankAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService){
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllAccounts(){
        List<BankAccountDTO> accounts = bankAccountService.getAllAccounts();
        return accounts.isEmpty()?
                ResponseEntity.noContent().build():
                ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        var response=bankAccountService.createAccount(bankAccountDTO);
        return response != null ?
                ResponseEntity.status(201).body(response):
                ResponseEntity.status(400).build();
    }
}
