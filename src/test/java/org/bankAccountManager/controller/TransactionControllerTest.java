package org.bankAccountManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bankAccountManager.DTO.request.TransactionRequestDTO;
import org.bankAccountManager.DTO.response.TransactionResponseDTO;
import org.bankAccountManager.entity.Transaction;
import org.bankAccountManager.service.implementations.TransactionServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.bankAccountManager.mapper.DTOResponseMapper.*;
import static org.bankAccountManager.mapper.DTORequestMapper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionServiceImplementation transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;

    @BeforeEach
    void setUp() {
        transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setId(1);
        transactionRequestDTO.setType("Deposit");
        transactionRequestDTO.setAmount(1000.0);

        transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setId(1);
        transactionResponseDTO.setType("Deposit");
        transactionResponseDTO.setAmount(1000.0);
    }

    @Test
    void createTransaction_ShouldReturn201() throws Exception {
        Mockito.when(transactionService.createTransaction(any())).thenReturn(toTransaction(transactionResponseDTO));

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Deposit"))
                .andExpect(jsonPath("$.amount").value(1000.0));
    }

    @Test
    void getTransactionById_ShouldReturnTransaction() throws Exception {
        Mockito.when(transactionService.getTransactionById(1)).thenReturn(toTransaction(transactionResponseDTO));

        mockMvc.perform(get("/transaction/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Deposit"))
                .andExpect(jsonPath("$.amount").value(1000.0));
    }

    @Test
    void getAllTransactions_ShouldReturnListOfTransactions() throws Exception {
        List<Transaction> transactionList = List.of(toTransaction(transactionResponseDTO));
        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactionList);
        mockMvc.perform(get("/transaction")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].type").value("Deposit"))
                .andExpect(jsonPath("$[0].amount").value(1000.0));
    }

    @Test
    void updateTransaction_ShouldReturnUpdatedTransaction() throws Exception {
        Mockito.when(transactionService.updateTransaction(any())).thenReturn(toTransaction(transactionResponseDTO));
        mockMvc.perform(put("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("Deposit"))
                .andExpect(jsonPath("$.amount").value(1000.0));
    }

    @Test
    void deleteTransaction_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(transactionService).deleteTransaction(any());
        mockMvc.perform(delete("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequestDTO)))
                .andExpect(status().isNoContent());
    }
}
