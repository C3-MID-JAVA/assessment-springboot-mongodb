package com.sofkau.usrv_accounts_manager.controller;


import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.services.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/card")
@Tag(name = "Card Management", description = "Operations related to card management")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(
            summary = "Create a new card",
            description = "Creates a new card with the provided details about client and account"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created account", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CardDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/create")
    public ResponseEntity<CardDTO> createAccount(@Valid @RequestBody CardDTO cardDTO) {

        var response =  cardService.createCard(cardDTO);
        return response  != null ? ResponseEntity.ok(response) : ResponseEntity.notFound().build();
    }
}
