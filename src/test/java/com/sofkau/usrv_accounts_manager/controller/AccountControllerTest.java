package com.sofkau.usrv_accounts_manager.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.ArrayList;



import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Should create a new account and return the complete body response")
    void createAccount() throws Exception {

        AccountDTO accountDTORequest = new AccountDTO(new ArrayList<>(),"12345",
                BigDecimal.valueOf(100), "DEBIT", "My cas", new ArrayList<>());
        AccountDTO accountDTOResponse = new AccountDTO(new ArrayList<>(),"12345",
                BigDecimal.valueOf(100), "DEBIT", "My cas", new ArrayList<>());

        given(accountService.createAccount(ArgumentMatchers.any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        mockMvc.perform(post("/api/v1/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTORequest)))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(accountDTOResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Should return an error about a null field")
    void createAccount_throws_Error() throws Exception {

        AccountDTO accountDTORequest = new AccountDTO(new ArrayList<>(),"12345",
                BigDecimal.valueOf(100), "DEBIT", null, new ArrayList<>());
        AccountDTO accountDTOResponse = new AccountDTO(new ArrayList<>(),"12345",
                BigDecimal.valueOf(100), "DEBIT", "My cas", new ArrayList<>());


        given(accountService.createAccount(ArgumentMatchers.any()))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));


        mockMvc.perform(post("/api/v1/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTORequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SOME FIELD(s) IN THE REQUEST HAS ERROR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", org.hamcrest.Matchers.hasItem("accountOwner: accountOwner cannot be empty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details", org.hamcrest.Matchers.hasItem("accountOwner: accountOwner cannot be null")));

    }
}