package org.example.financespro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.facade.FinanceFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FinanceFacade financeFacade;

  @Test
  void shouldProcessTransactionSuccessfully() throws Exception {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("123");
    request.setTransactionType("ATM_WITHDRAWAL");
    request.setTransactionAmount(BigDecimal.valueOf(500));

    TransactionResponseDto response = new TransactionResponseDto();
    response.setTransactionType("ATM_WITHDRAWAL");
    response.setTransactionAmount(BigDecimal.valueOf(500));
    response.setTransactionCost(BigDecimal.valueOf(1.00));
    response.setRemainingBalance(BigDecimal.valueOf(499));

    Mockito.when(financeFacade.processTransaction(any(TransactionRequestDto.class)))
        .thenReturn(response);

    mockMvc
        .perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.transactionType").value("ATM_WITHDRAWAL"))
        .andExpect(jsonPath("$.transactionAmount").value(500))
        .andExpect(jsonPath("$.transactionCost").value(1.00))
        .andExpect(jsonPath("$.remainingBalance").value(499));
  }

  @Test
  void shouldReturnBadRequestForInvalidTransaction() throws Exception {
    TransactionRequestDto request = new TransactionRequestDto();
    request.setAccountId("");
    request.setTransactionType("");
    request.setTransactionAmount(BigDecimal.valueOf(-1));

    mockMvc
        .perform(
            post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.accountId").value("Account ID is required."))
        .andExpect(jsonPath("$.transactionType").value("Transaction type is required."))
        .andExpect(jsonPath("$.transactionAmount").value("Transaction amount must be positive."));
  }
}
