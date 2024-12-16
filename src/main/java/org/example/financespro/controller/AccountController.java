package org.example.financespro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.facade.FinanceFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Accounts", description = "Endpoints for managing accounts")
@Validated // Habilita validaciones en los DTO
public class AccountController {

  private final FinanceFacade financeFacade;

  public AccountController(FinanceFacade financeFacade) {
    this.financeFacade = financeFacade;
  }

  @PostMapping
  @Operation(
      summary = "Create an account",
      description = "Creates a new account with the provided details.",
      responses = {
        @ApiResponse(
            responseCode = "201",
            description = "Account successfully created",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data",
            content = @Content(mediaType = "application/json"))
      })
  public ResponseEntity<AccountResponseDto> createAccount(
      @Validated @RequestBody AccountRequestDto request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(financeFacade.createAccount(request));
  }

  @GetMapping("/{accountNumber}")
  @Operation(
      summary = "Get account details",
      description = "Retrieves details of an account by its account number.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Account details retrieved",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content(mediaType = "application/json"))
      })
  public ResponseEntity<AccountResponseDto> getAccount(
      @Parameter(description = "The unique account number") @PathVariable String accountNumber) {
    return ResponseEntity.ok(financeFacade.getAccountDetails(accountNumber));
  }
}
