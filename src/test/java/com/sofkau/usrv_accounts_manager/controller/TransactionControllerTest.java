package com.sofkau.usrv_accounts_manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.dto.TransactionDTO;
import com.sofkau.usrv_accounts_manager.services.AccountService;
import com.sofkau.usrv_accounts_manager.services.TransactionService;
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

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should create a new transaction and return the complete body response")
    void makeTransaction() throws Exception {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setCardNumber("123456789");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456");
        TransactionDTO transactionDTOReq = new TransactionDTO("Test Transaction",BigDecimal.valueOf(10),
                "ATM", BigDecimal.valueOf(0), accountDTO, cardDTO);
        TransactionDTO transactionDTORes = new TransactionDTO("Test Transaction",BigDecimal.valueOf(10),
                "ATM", BigDecimal.valueOf(0), accountDTO, cardDTO);

        given(transactionService.createTransaction(ArgumentMatchers.any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        mockMvc.perform(post("/api/v1/transaction/make")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDTOReq)))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(transactionDTORes)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}