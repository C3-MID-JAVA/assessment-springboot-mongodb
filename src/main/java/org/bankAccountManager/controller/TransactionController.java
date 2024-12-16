package org.bankAccountManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bankAccountManager.DTO.request.TransactionRequestDTO;
import org.bankAccountManager.DTO.response.TransactionResponseDTO;
import org.bankAccountManager.mapper.DTOResponseMapper;
import org.bankAccountManager.service.implementations.TransactionServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankAccountManager.mapper.DTORequestMapper.toTransaction;
import static org.bankAccountManager.mapper.DTOResponseMapper.toTransactionResponseDTO;

@Tag(name = "Transaction Management", description = "Endpoints for managing transactions")
@RestController
@RequestMapping("/transaction")
public class TransactionController {


    private final TransactionServiceImplementation transactionService;

    public TransactionController(TransactionServiceImplementation transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Create a new transaction", description = "Add a new transaction to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toTransactionResponseDTO(transactionService.createTransaction(toTransaction(transaction))));
    }

    @Operation(summary = "Retrieve a transaction by ID", description = "Get details of a transaction by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @GetMapping("/id")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(toTransactionResponseDTO(transactionService.getTransactionById(transaction.getId())));
    }

    @Operation(summary = "Retrieve all transactions", description = "Get a list of all transactions in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions().stream()
                .map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Retrieve transactions by branch ID", description = "Get a list of transactions for a specific branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Branch not found")
    })
    @GetMapping("/branch")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByBranchId(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.getTransactionsByBranchId(transaction.getBranch().getId())
                .stream().map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Retrieve transactions by destination account ID", description = "Get transactions linked to a specific destination account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/account/destination")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByDestinationAccountId(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.getTransactionsByDestinationAccountId(transaction.getDestination_account().getId())
                .stream().map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Retrieve transactions by source account ID", description = "Get transactions linked to a specific source account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/account/source")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsBySourceAccountId(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.getTransactionsBySourceAccountId(transaction.getSource_account().getId())
                .stream().map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Retrieve transactions by date", description = "Get transactions made on a specific date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully")
    })
    @GetMapping("/date")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByDate(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.getTransactionsByDate(transaction.getDate())
                .stream().map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Retrieve transactions by type", description = "Get transactions of a specific type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction type not found")
    })
    @GetMapping("/type")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByType(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.getTransactionsByType(transaction.getType())
                .stream().map(DTOResponseMapper::toTransactionResponseDTO).toList());
    }

    @Operation(summary = "Update a transaction", description = "Update details of an existing transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @PutMapping
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@RequestBody TransactionRequestDTO transaction) {
        return ResponseEntity.ok(toTransactionResponseDTO(transactionService.updateTransaction(toTransaction(transaction))));
    }

    @Operation(summary = "Delete a transaction", description = "Remove a transaction from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Transaction not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteTransaction(@RequestBody TransactionRequestDTO transaction) {
        transactionService.deleteTransaction(transactionService.getTransactionById(transaction.getId()));
        return ResponseEntity.noContent().build();
    }
}
