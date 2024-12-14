package com.bankmanagement.bankmanagement.controller;

import com.bankmanagement.bankmanagement.dto.AccountRequestDTO;
import com.bankmanagement.bankmanagement.dto.AccountResponseDTO;
import com.bankmanagement.bankmanagement.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@Valid @RequestBody AccountRequestDTO accountRequestDTO){
        AccountResponseDTO accountResponseDTO = accountService.create(accountRequestDTO);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/user")
    public ResponseEntity<List<AccountResponseDTO>> getAllByUserId(@PathVariable UUID userId){
        List<AccountResponseDTO> accountResponseDTOS = accountService.getAllByUserId(userId);
        return ResponseEntity.ok(accountResponseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getByAccountNumber(@PathVariable String id){
        AccountResponseDTO accountResponseDTO = accountService.findByAccountNumber(id);
        return ResponseEntity.ok(accountResponseDTO);
    }

}
