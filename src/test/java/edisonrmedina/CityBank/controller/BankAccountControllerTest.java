package edisonrmedina.CityBank.controller;

import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.service.BankAccountService;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BankAccountControllerTest {

    @MockitoBean
    private BankAccountServiceImp bankAccountServiceImp;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetBankAccount_Success() throws Exception {
        // Arrange: Crear un objeto BankAccount y configurar el mock del servicio
        String accountId = "675e962784c89c424d3bc7b6";
        BankAccount mockAccount = new BankAccount("675e962784c89c424d3bc7b6", "John Doe", BigDecimal.valueOf(1000));

        // Simular la respuesta del servicio
        Mockito.when(bankAccountServiceImp.getBankAccount(accountId)).thenReturn(Optional.of(mockAccount));

        // Act & Assert: Hacer la solicitud GET y verificar que la respuesta sea 200 OK
        mockMvc.perform((RequestBuilder) get("/bank-accounts/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolder").value("John Doe"))
                .andExpect(jsonPath("$.balance").value(1000));
    }

    @Test
    void testGetBankAccount_NotFound() throws Exception {
        // Arrange: Simular que no se encuentra la cuenta bancaria
        String accountId = "12345";
        Mockito.when(bankAccountServiceImp.getBankAccount(accountId)).thenReturn(Optional.empty());

        // Act & Assert: Hacer la solicitud GET y verificar que la respuesta sea 404 Not Found
        mockMvc.perform((RequestBuilder) get("/bank-accounts/{id}", accountId))
                .andExpect(status().isNotFound());
    }

}
