package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.CardRequestDTO;
import ec.com.example.bank_account.dto.CardResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.entity.Card;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.CardMapper;
import ec.com.example.bank_account.repository.CardRepository;
import ec.com.example.bank_account.service.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private TypeAccountResponseDTO typeAccountResponseDTO;
    private AccountResponseDTO accountResponseDTO;
    private CardRequestDTO cardRequestDTO;
    private CardResponseDTO cardResponseDTO;

    @BeforeEach
    public void setUp() {
        card = new Card();
        card.setHolderName("Diego Loor");
        card.setLimitation(new BigDecimal(1000));
        card.setCvcCode("234");
        card.setStatus("Loor");
        card.setExpirationDate(new Date());
        card.setStatus("ACTIVE");

        UserResponseDTO userResponse = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");

        typeAccountResponseDTO = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");
        accountResponseDTO = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponseDTO);

        cardRequestDTO = new CardRequestDTO("Diego Loor", new BigDecimal(1000),
                "234", new Date(), "ACTIVE", "test12");
        cardResponseDTO = new CardResponseDTO("2200000000", new BigDecimal(100),
                "234", new Date(), null, accountResponseDTO);
    }

    @Test
    void createCard_ShouldReturnCreatedEntity_WhenCardIsCreatedSuccessfully() {
        when(cardMapper.mapToEntity(cardRequestDTO)).thenReturn(card);
        when(cardRepository.save(card)).thenReturn(card);
        when(cardMapper.mapToDTO(card)).thenReturn(cardResponseDTO);

        CardResponseDTO response = cardService.createCard(cardRequestDTO);

        assertNotNull(response);
        assertEquals(cardResponseDTO, response);
        verify(cardRepository, times(1)).save(card);
        verify(cardMapper, times(1)).mapToDTO(card);
    }

    @Test
    void getAllCards_ShouldReturnAllCards_WhenCardsAreFound() {
        List<Card> cards = List.of(card);
        List<CardResponseDTO> cardResponseDTOs = List.of(cardResponseDTO);

        when(cardRepository.findAll()).thenReturn(cards);
        when(cardMapper.mapToDTO(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            return new CardResponseDTO(card.getHolderName(), card.getLimitation(), card.getCvcCode(),
                    card.getExpirationDate(), card.getStatus(), accountResponseDTO);
        });

        List<CardResponseDTO> result = cardService.getAllCards();

        assertNotNull(result);
        assertEquals(cardResponseDTOs.size(), result.size());
        verify(cardRepository, times(1)).findAll();
        verify(cardMapper, times(1)).mapToDTO(any(Card.class));
    }

    @Test
    void getAllCards_ShouldThrowExceptionWhenNoCardsFound() {
        when(cardRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> cardService.getAllCards());
        assertEquals("No cards records found.", exception.getMessage());
    }

}