package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.CardRequestDTO;
import ec.com.example.bank_account.dto.CardResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardControllerTest {

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;

    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    void setUp() {
        UserResponseDTO userResponse = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
        TypeAccountResponseDTO typeAccountResponse = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");
        accountResponseDTO = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponse);
    }

    @Test
    void createCard_ShouldReturnCreatedStatus_WhenCardIsCreatedSuccessfully() {
        CardRequestDTO cardRequest = new CardRequestDTO("Diego Loor", new BigDecimal(1000),
                "234", new Date(), "ACTIVE", "test12");
        CardResponseDTO cardResponse = new CardResponseDTO("2200000000", new BigDecimal(100),
                "234", new Date(), null, accountResponseDTO);
        when(cardService.createCard(cardRequest)).thenReturn(cardResponse);

        ResponseEntity<CardResponseDTO> response = cardController.createCard(cardRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cardResponse, response.getBody());
        verify(cardService, times(1)).createCard(cardRequest);
    }

    @Test
    void createCard_ShouldReturnBadRequest_WhenCardServiceReturnsNull() {
        CardRequestDTO cardRequest = new CardRequestDTO("Diego Loor", new BigDecimal(1000),
                "234", new Date(), "ACTIVE", "test12");
        when(cardService.createCard(cardRequest)).thenReturn(null);

        ResponseEntity<CardResponseDTO> response = cardController.createCard(cardRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(cardService, times(1)).createCard(cardRequest);
    }

    @Test
    void getAllCards_ShouldReturnOkStatus_WhenCardsAreRetrievedSuccessfully() {
        List<CardResponseDTO> cards = List.of(new CardResponseDTO("Diego Loor", new BigDecimal(1000),
                "234", new Date(), "ACTIVE", accountResponseDTO));
        when(cardService.getAllCards()).thenReturn(cards);

        ResponseEntity<List<CardResponseDTO>> response = cardController.getAllCards();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cards, response.getBody());
        verify(cardService, times(1)).getAllCards();
    }

    @Test
    void getAllCards_ShouldReturnNoContent_WhenNoCardsAreFound() {
        when(cardService.getAllCards()).thenReturn(Collections.emptyList());

        ResponseEntity<List<CardResponseDTO>> response = cardController.getAllCards();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cardService, times(1)).getAllCards();
    }
}

