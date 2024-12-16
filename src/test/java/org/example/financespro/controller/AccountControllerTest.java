package org.example.financespro.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.facade.FinanceFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FinanceFacade financeFacade;

  @Test
  void shouldCreateAccountSuccessfully() throws Exception {
    AccountRequestDto request = new AccountRequestDto();
    request.setNumber("123456");
    request.setInitialBalance(BigDecimal.valueOf(1000));

    AccountResponseDto response = new AccountResponseDto();
    response.setAccountNumber("123456");
    response.setBalance(1000.0);

    Mockito.when(financeFacade.createAccount(any(AccountRequestDto.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.accountNumber").value("123456"))
        .andExpect(jsonPath("$.balance").value(1000.0));
  }

  @Test
  void shouldGetAccountDetailsSuccessfully() throws Exception {
    AccountResponseDto response = new AccountResponseDto();
    response.setAccountNumber("123456");
    response.setBalance(1000.0);

    Mockito.when(financeFacade.getAccountDetails("123456")).thenReturn(response);

    mockMvc
        .perform(get("/accounts/123456").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accountNumber").value("123456"))
        .andExpect(jsonPath("$.balance").value(1000.0));
  }

  @Test
  void shouldReturnBadRequestForInvalidAccountNumber() throws Exception {
    AccountRequestDto request = new AccountRequestDto();
    request.setNumber(""); // Número de cuenta inválido
    request.setInitialBalance(BigDecimal.valueOf(1000));

    mockMvc
        .perform(
            post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isBadRequest()) // Verifica el estado HTTP 400
        .andExpect(jsonPath("$.number").value("Account number is required."));
  }
}
