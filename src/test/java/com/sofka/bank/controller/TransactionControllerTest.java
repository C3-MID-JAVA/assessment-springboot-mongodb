package com.sofka.bank.controller;

import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.dto.TransactionDTO;
import com.sofka.bank.entity.TransactionType;
import com.sofka.bank.exceptions.AccountNotFoundException;
import com.sofka.bank.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private String accountId;
    private TransactionDTO validTransactionDTO;

    @BeforeEach
    public void setup() {
        accountId = "12345";

        validTransactionDTO = new TransactionDTO("40", TransactionType.WITHDRAW_ATM, 1000.0, 0, LocalDateTime.now(),
                "ATM Withdrawal", new BankAccountDTO(accountId, "1000008", "John Doe", 5000.0, new ArrayList<>()));
    }

    @Test
    @DisplayName("Should return a 200 OK response when retrieving balance for specified account")
    public void testGetGlobalBalance_Success() throws Exception {
        Mockito.when(transactionService.getGlobalBalance(accountId)).thenReturn(5000.0);

        mockMvc.perform(get("/transactions/balance/{accountId}", accountId))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").value(5000.0));
    }

    @Test
    @DisplayName("Should return a 404 Not Found response when account is not found")
    public void testGetGlobalBalance_AccountNotFound() throws Exception {
        Mockito.when(transactionService.getGlobalBalance(accountId)).thenThrow(new AccountNotFoundException("Account not found"));

        mockMvc.perform(get("/transactions/balance/{accountId}", accountId))
                .andExpect(status().isNotFound())
                .andExpect((ResultMatcher) content().string("Account not found"));
    }

    @Test
    @DisplayName("Should return a 200 OK Response when transaction is successfully registered")
    public void testRegisterTransaction_Success() throws Exception {
        Mockito.when(transactionService.registerTransaction(Mockito.anyString(), Mockito.any(TransactionDTO.class)))
                .thenReturn(validTransactionDTO);

        mockMvc.perform(post("/transactions/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionType\": \"WITHDRAW_ATM\", \"amount\": 1000.0, \"description\": \"ATM Withdrawal\"}"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.description").value("ATM Withdrawal"))
                .andExpect((ResultMatcher) jsonPath("$.amount").value(1000.0));
    }


    @Test
    @DisplayName("Should return 404 Not Found HTTP Response when the account for which transaction is being " +
            "registered is not found")
    public void testRegisterTransaction_AccountNotFound() throws Exception {
        Mockito.when(transactionService.registerTransaction(Mockito.anyString(), Mockito.any(TransactionDTO.class)))
                .thenThrow(new AccountNotFoundException("Account with ID " + accountId + " not found"));

        mockMvc.perform(post("/transactions/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionType\": \"WITHDRAW_ATM\", \"amount\": 1000.0, \"description\": \"ATM Withdrawal\"}"))
                .andExpect(status().isNotFound())
                .andExpect((ResultMatcher) content().string("Account with ID " + accountId + " not found"));
    }

    @Test
    @DisplayName("Should return a 400 Bad Request Response when transaction is being registered with invalid inputs; " +
            "in this case, missing required fields")
    public void testRegisterTransaction_BadRequest() throws Exception {
        mockMvc.perform(post("/transactions/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"transactionType\": \"WITHDRAW_ATM\", \"amount\": 1000.0}"))
                .andExpect(status().isBadRequest());
    }
}
