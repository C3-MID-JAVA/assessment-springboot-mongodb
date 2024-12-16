package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.TypeAccountRequestDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.service.TypeAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Account type RESTful", description = "Endpoints for Account type management.")
@RestController
@RequestMapping("/api/types-account")
public class TypeAccountController {

    private final TypeAccountService typeAccountService;

    public TypeAccountController(TypeAccountService typeAccountService) {
        this.typeAccountService = typeAccountService;
    }

    @Operation(summary = "Create new account type", description = "Create a new account type from the request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account type created successfully."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @PostMapping
    public ResponseEntity<TypeAccountResponseDTO> createTypeAccount(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Body with the account type data to be created", required = true)
            @Valid @RequestBody TypeAccountRequestDTO typeAccount) {
        TypeAccountResponseDTO response = typeAccountService.createTypeAccount(typeAccount);
        return response != null ?
                ResponseEntity.status(201).body(response) :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get all account type", description = "Get all registered account types.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully obtained all registered account types."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @GetMapping
    public ResponseEntity<List<TypeAccountResponseDTO>> getTypesAccount() {
        List<TypeAccountResponseDTO> response = typeAccountService.getAllTypeAccount();
        return !response.isEmpty() ?
                ResponseEntity.ok().body(response) :
                ResponseEntity.noContent().build();
    }
}