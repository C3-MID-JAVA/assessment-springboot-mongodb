package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.TypeTransactionRequestDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.service.TypeTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Transaction type RESTful", description = "Endpoints for Transaction type management.")
@RestController
@RequestMapping("/api/types-transaction")
public class TypeTransactionController {
    private final TypeTransactionService typeTransactionService;

    public TypeTransactionController(TypeTransactionService typeTransactionService) {
        this.typeTransactionService = typeTransactionService;
    }

    @Operation(summary = "Create new transaction type", description = "Create a new transaction type from the request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transaction type created successfully."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @PostMapping
    public ResponseEntity<TypeTransactionResponseDTO> createTypeTransaction(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Body with the transaction type data to be created", required = true)
            @Valid @RequestBody TypeTransactionRequestDTO typeTransaction) {
        TypeTransactionResponseDTO response = typeTransactionService.createTypeTransaction(typeTransaction);
        return response != null ?
                ResponseEntity.status(201).body(response) :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get all transactions type", description = "Get all registered transactions types.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully obtained all registered transactions types."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @GetMapping
    public ResponseEntity<List<TypeTransactionResponseDTO>> getAllTypeTransactions() {
        List<TypeTransactionResponseDTO> response = typeTransactionService.getAllTypeTransactions();
        return !response.isEmpty() ?
                ResponseEntity.ok().body(response) :
                ResponseEntity.noContent().build();
    }

}