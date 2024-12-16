package com.sofkau.usrv_accounts_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.services.AccountService;
import com.sofkau.usrv_accounts_manager.services.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = CardController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @MockitoBean
    private CardService cardService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }
    @Test
    @DisplayName("Should create a new card and return the complete body response")
    void createAccount() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        CardDTO cardDTORequest = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO, null
                );
        CardDTO cardDTOResponse = new CardDTO("CARD TEST", "123456789",
                "TDEBIT","ACTIVE", "12-12-2024",
                BigDecimal.valueOf(1000), "TEST HOLDER",
                accountDTO, null
        );


        given(cardService.createCard(ArgumentMatchers.any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        mockMvc.perform(post("/api/v1/card/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardDTORequest)))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(cardDTOResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}