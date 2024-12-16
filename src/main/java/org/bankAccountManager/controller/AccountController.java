package org.bankAccountManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bankAccountManager.DTO.request.AccountRequestDTO;
import org.bankAccountManager.DTO.response.AccountResponseDTO;
import org.bankAccountManager.mapper.DTOResponseMapper;
import org.bankAccountManager.service.implementations.AccountServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankAccountManager.mapper.DTORequestMapper.toAccount;
import static org.bankAccountManager.mapper.DTOResponseMapper.toAccountResponseDTO;

@Tag(name = "Account Management", description = "Endpoints for managing accounts")
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServiceImplementation accountService;

    public AccountController(AccountServiceImplementation accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create a new account", description = "Create a new account with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toAccountResponseDTO(accountService.createAccount(toAccount(account))));
    }

    @Operation(summary = "Retrieve an account by ID", description = "Get the details of an account using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/id")
    public ResponseEntity<AccountResponseDTO> getAccountById(@RequestBody AccountRequestDTO account) {
        return ResponseEntity.ok(toAccountResponseDTO(accountService.getAccountById(account.getId())));
    }

    @Operation(summary = "Retrieve an account by Customer ID", description = "Get the details of an account using the customer's ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/customer")
    public ResponseEntity<AccountResponseDTO> getAccountByCustomerId(@RequestBody AccountRequestDTO account) {
        return ResponseEntity.ok(toAccountResponseDTO(accountService.getAccountByCustomerId(account.getCustomer().getId())));
    }

    @Operation(summary = "Retrieve all accounts", description = "Get the list of all accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of accounts retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts().stream()
                .map(DTOResponseMapper::toAccountResponseDTO).toList());
    }

    @Operation(summary = "Update an account", description = "Update an existing account with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping
    public ResponseEntity<AccountResponseDTO> updateAccount(@RequestBody AccountRequestDTO account) {
        return ResponseEntity.ok(toAccountResponseDTO(accountService.updateAccount(toAccount(account))));
    }

    @Operation(summary = "Delete an account", description = "Delete an account by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountRequestDTO account) {
        accountService.deleteAccount(accountService.getAccountById(account.getId()));
        return ResponseEntity.noContent().build();
    }
}