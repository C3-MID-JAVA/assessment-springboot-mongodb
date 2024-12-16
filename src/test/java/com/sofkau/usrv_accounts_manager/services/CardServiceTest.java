package com.sofkau.usrv_accounts_manager.services;

import com.sofkau.usrv_accounts_manager.Utils.Utils;
import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.model.AccountModel;
import com.sofkau.usrv_accounts_manager.model.CardModel;
import com.sofkau.usrv_accounts_manager.repository.AccountRepository;
import com.sofkau.usrv_accounts_manager.repository.CardRepository;
import com.sofkau.usrv_accounts_manager.services.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.smartcardio.Card;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CardServiceTest {


    @Mock
    private CardRepository cardRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private CardModel card;
    private CardDTO cardDTO;
    private AccountDTO accountDTO;
    private AccountModel account;

    @BeforeEach
    void setUp() {
        cardRepository.deleteAll();
        card = new CardModel();
        card.setCardNumber("123456789");
        accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        cardDTO = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO, null
        );
        account = new AccountModel();
        account.setAccountNumber("1234599");
    }

    @Test
    @DisplayName("Should find a existing cvv and return true")
    void existsCvv_Exists_ReturnTrue() {
        String existingCvv = "123";
        when(cardRepository.existsByCardCVV(existingCvv)).thenReturn(true);

        boolean result = cardService.existsCvv(existingCvv);
        assertTrue(result);
    }

    @Test
    @DisplayName("Should not find a existing cvv and return false")
    void existsCvv_Exists_ReturnFalse() {
        String notExistingCvv = "123";
        when(cardRepository.existsByCardCVV(notExistingCvv)).thenReturn(false);

        boolean result = cardService.existsCvv(notExistingCvv);
        assertFalse(result);
    }


    @Test
    @DisplayName("Should create a new card when card does NOT already exist")
    void createCard_success() throws Exception {
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.empty());
        when(cardRepository.save(any(CardModel.class))).thenReturn(card);

        CardDTO result = cardService.createCard(cardDTO);
        assertNotNull(result);
        assertEquals(cardDTO.getCardNumber(), result.getCardNumber());

    }

    @Test
    @DisplayName("Should NOT create a new card when card already exist")
    void createCard_cardAlreadyExists() throws Exception {

        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cardService.createCard(cardDTO);
        });

        assertEquals("Card already exists", exception.getMessage());


    }
}