package com.sofka.bank.controller;

import com.sofka.bank.dto.TransactionDTO;
import com.sofka.bank.service.TransactionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<Double> getGlobalBalance(@PathVariable Long accountId) {
        logger.info("Fetching global balance for account ID: {}", accountId);
        Double balance = transactionService.getGlobalBalance(accountId);
        logger.info("Global balance for account ID {}: {}", accountId, balance);
        return ResponseEntity.ok(balance);
    }


    @PostMapping("/{accountId}")
    public ResponseEntity<TransactionDTO> registerTransaction(
            @PathVariable Long accountId, @Valid @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.validar();

        TransactionDTO registeredTransaction = transactionService.registerTransaction(accountId, transactionDTO);
        return ResponseEntity.ok(registeredTransaction);
    }
}