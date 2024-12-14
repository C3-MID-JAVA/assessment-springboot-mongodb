package com.bankmanagement.bankmanagement.controller;

import com.bankmanagement.bankmanagement.dto.TransactionRequestDTO;
import com.bankmanagement.bankmanagement.dto.TransactionResponseDTO;
import com.bankmanagement.bankmanagement.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO transactionResponseDTO = transactionService.create(transactionRequestDTO);
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}/account")
    public ResponseEntity<List<TransactionResponseDTO>> getAllByAccountNumber(@PathVariable String accountNumber){
        List<TransactionResponseDTO> transactionResponseDTOS = transactionService.getAllByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactionResponseDTOS);
    }
}
