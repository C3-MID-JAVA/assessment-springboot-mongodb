package com.sofka.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BankAccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountController bankAccountController;

    private BankAccountDTO validAccountDTO;

    @BeforeEach
    public void setup() {
        validAccountDTO = new BankAccountDTO(null, "1000008", "John Doe", 5000.0, new ArrayList<>());
    }

    @Test
    @DisplayName("Should retrieve all accounts and return 200 OK")
    public void testGetAllAccounts_Success() throws Exception {
        List<BankAccountDTO> accounts = Arrays.asList(validAccountDTO);
        Mockito.when(bankAccountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(accounts)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should return an empty list with a 204 no content response when no accounts are found")
    public void testGetAllAccounts_NoContent() throws Exception {
        Mockito.when(bankAccountService.getAllAccounts()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return a successfully created account with 201 CREATED")
    public void testCreateAccount_Success() throws Exception {
        Mockito.when(bankAccountService.createAccount(Mockito.any(BankAccountDTO.class)))
                .thenReturn(validAccountDTO);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountHolder\": \"John Doe\", \"accountNumber\": \"1000008\", " +
                                "\"globalBalance\": 5000.0}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(validAccountDTO)));
    }

    @Test
    @DisplayName("Should return a 400 Bad Request response when trying to create account with invalid request body; " +
            "in this case, a missing accountHolder field")
    public void testCreateAccount_BadRequest() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\": \"1000008\", \"globalBalance\": 5000.0}"))
                .andExpect(status().isBadRequest());
    }
}
