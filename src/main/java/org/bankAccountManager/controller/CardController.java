package org.bankAccountManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bankAccountManager.DTO.request.CardRequestDTO;
import org.bankAccountManager.DTO.response.CardResponseDTO;
import org.bankAccountManager.mapper.DTOResponseMapper;
import org.bankAccountManager.service.implementations.CardServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankAccountManager.mapper.DTORequestMapper.toCard;
import static org.bankAccountManager.mapper.DTOResponseMapper.toCardResponseDTO;

@Tag(name = "Card Management", description = "Endpoints for managing cards")
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardServiceImplementation cardService;

    public CardController(CardServiceImplementation cardService) {
        this.cardService = cardService;
    }

    @Operation(summary = "Create a new card", description = "Add a new card to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Card created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CardResponseDTO> createCard(@RequestBody CardRequestDTO card) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toCardResponseDTO(cardService.createCard(toCard(card))));
    }

    @Operation(summary = "Retrieve a card by ID", description = "Get details of a card by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/id")
    public ResponseEntity<CardResponseDTO> getCardById(@RequestBody CardRequestDTO card) {
        return ResponseEntity.ok(toCardResponseDTO(cardService.getCardById(card.getId())));
    }

    @Operation(summary = "Retrieve a card by number", description = "Get details of a card by its unique number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @GetMapping("/number")
    public ResponseEntity<CardResponseDTO> getCardByNumber(@RequestBody CardRequestDTO card) {
        return ResponseEntity.ok(toCardResponseDTO(cardService.getCardByNumber(card.getCard_number())));
    }

    @Operation(summary = "Retrieve all cards", description = "Get a list of all available cards")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CardResponseDTO>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards()
                .stream().map(DTOResponseMapper::toCardResponseDTO).toList());
    }

    @Operation(summary = "Retrieve cards by account ID", description = "Get all cards associated with a specific account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/account")
    public ResponseEntity<List<CardResponseDTO>> getCardsByAccountId(@RequestBody CardRequestDTO card) {
        return ResponseEntity.ok(cardService.getCardsByAccountId(card.getAccount().getId())
                .stream().map(DTOResponseMapper::toCardResponseDTO).toList());
    }

    @Operation(summary = "Retrieve cards by type", description = "Get all cards of a specific type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cards retrieved successfully")
    })
    @GetMapping("/type")
    public ResponseEntity<List<CardResponseDTO>> getCardsByType(@RequestBody CardRequestDTO card) {
        return ResponseEntity.ok(cardService.getCardsByType(card.getCard_type())
                .stream().map(DTOResponseMapper::toCardResponseDTO).toList());
    }

    @Operation(summary = "Update a card", description = "Update details of an existing card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @PutMapping
    public ResponseEntity<CardResponseDTO> updateCard(@RequestBody CardRequestDTO card) {
        return ResponseEntity.ok(toCardResponseDTO(cardService.updateCard(toCard(card))));
    }

    @Operation(summary = "Delete a card", description = "Remove a card from the system by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Card deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Card not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteCard(@RequestBody CardRequestDTO card) {
        cardService.deleteCard(cardService.getCardById(card.getId()));
        return ResponseEntity.noContent().build();
    }
}