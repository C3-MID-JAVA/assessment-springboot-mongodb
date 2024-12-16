package com.sofka.bank.controller;

import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.service.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Get all accounts", description = "Retrieve all accounts with their respective details")
    @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")
    @ApiResponse(responseCode = "204", description = "No content not found")
    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllAccounts(){
        List<BankAccountDTO> accounts = bankAccountService.getAllAccounts();
        return accounts.isEmpty()?
                ResponseEntity.noContent().build():
                ResponseEntity.ok(accounts);
    }

    @Operation(summary = "Create a new account", description = "Creates a new account in the system.")
    @ApiResponse(responseCode = "201", description = "Account created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountDTO response=bankAccountService.createAccount(bankAccountDTO);
        return response != null ?
                ResponseEntity.status(201).body(response):
                ResponseEntity.status(400).build();
    }
}
