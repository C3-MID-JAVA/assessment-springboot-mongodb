package org.bankAccountManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bankAccountManager.DTO.request.AccountRequestDTO;
import org.bankAccountManager.DTO.response.AccountResponseDTO;
import org.bankAccountManager.service.implementations.AccountServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.bankAccountManager.mapper.DTOResponseMapper.toAccount;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImplementation accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountRequestDTO accountRequest = new AccountRequestDTO();
    private AccountResponseDTO accountResponse = new AccountResponseDTO();

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        accountRequest.setId(1);
        accountRequest.setAccount_number("1234567890");
        accountRequest.setAccount_type("Savings");
        accountRequest.setBalance(new BigDecimal("1000"));
        accountRequest.setCards();
        accountRequest.setTransactions();

        accountResponse.setId(1);
        accountResponse.setAccount_number("1234567890");
        accountResponse.setAccount_type("Savings");
        accountResponse.setBalance(new BigDecimal("1000"));
        accountResponse.setCards();
        accountResponse.setTransactions();
    }

    @Test
    void createAccount() throws Exception {
        when(accountService.createAccount(any())).thenReturn(toAccount(accountResponse));
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.accountType").value("Savings"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void getAccountById() throws Exception {
        when(accountService.getAccountById(1)).thenReturn(toAccount(accountResponse));
        mockMvc.perform(get("/accounts/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.accountType").value("Savings"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void getAccountByCustomerId() throws Exception {
        when(accountService.getAccountByCustomerId(1)).thenReturn(toAccount(accountResponse));
        mockMvc.perform(get("/accounts/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.accountType").value("Savings"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void updateAccount() throws Exception {
        when(accountService.updateAccount(any())).thenReturn(toAccount(accountResponse));
        mockMvc.perform(put("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.accountType").value("Savings"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void deleteAccount() throws Exception {
        AccountRequestDTO accountRequest = new AccountRequestDTO();
        accountRequest.setId(1);
        mockMvc.perform(delete("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isNoContent());
    }
}
