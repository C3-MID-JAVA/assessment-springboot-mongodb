package org.example.financespro.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.facade.FinanceFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transactions", description = "Endpoints for managing transactions")
@Validated // Habilitar validaciones
public class TransactionController {

  private final FinanceFacade financeFacade;

  public TransactionController(FinanceFacade financeFacade) {
    this.financeFacade = financeFacade;
  }

  @PostMapping
  @Operation(
      summary = "Process a transaction",
      description = "Processes a transaction for a given account.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Transaction processed successfully",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid transaction data",
            content = @Content(mediaType = "application/json"))
      })
  public ResponseEntity<TransactionResponseDto> processTransaction(
      @Validated @RequestBody TransactionRequestDto request) {
    return ResponseEntity.ok(financeFacade.processTransaction(request));
  }
}
