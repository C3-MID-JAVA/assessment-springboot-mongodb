package edisonrmedina.CityBank.controller;

import edisonrmedina.CityBank.dto.TransactionDTO;
import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.mapper.Mapper;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import edisonrmedina.CityBank.service.impl.TransactionsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class TransactionsControllerTest {

    @Mock
    private TransactionsServiceImpl transactionsService;

    @Mock
    private BankAccountServiceImp bankAccountService;

    @InjectMocks
    private TransactionsController transactionsController;

    private MockMvc mockMvc;

    public TransactionsControllerTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build();
    }

    @Test
    void testGetAllTransactions() throws Exception {
        // Preparar datos de prueba - Lista de TransactionDTO
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(),
                new TransactionDTO()
        );

        // Simular el comportamiento del servicio
        when(transactionsService.getAllTransactions()).thenReturn(transactions);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2)) // Verifica que haya dos transacciones
                .andExpect(jsonPath("$[0].transactionId").value(1)) // Verifica el id de la primera transacción
                .andExpect(jsonPath("$[0].type").value("deposit")) // Verifica el tipo de la primera transacción
                .andExpect(jsonPath("$[0].amount").value(100.0)) // Verifica la cantidad de la primera transacción
                .andExpect(jsonPath("$[0].date").value("2024-12-16")) // Verifica la fecha de la primera transacción
                .andExpect(jsonPath("$[1].transactionId").value(2)) // Verifica el id de la segunda transacción
                .andExpect(jsonPath("$[1].type").value("withdrawal")) // Verifica el tipo de la segunda transacción
                .andExpect(jsonPath("$[1].amount").value(50.0)) // Verifica la cantidad de la segunda transacción
                .andExpect(jsonPath("$[1].date").value("2024-12-17")); // Verifica la fecha de la segunda transacción

        // Verificar que el servicio fue llamado correctamente
        verify(transactionsService, times(1)).getAllTransactions();
    }


    @Test
    void testCreateTransaction() throws Exception {
        // Preparar datos de prueba
        String accountId = "12345";
        TransactionDTO transactionRequest = new TransactionDTO();
        BankAccount bankAccount = new BankAccount();
        TransactionDTO createdTransaction = new TransactionDTO( );

        // Simular el comportamiento del servicio
        when(bankAccountService.getBankAccount(accountId)).thenReturn(Optional.of(bankAccount));
        when(transactionsService.createTransaction(any())).thenReturn(Mapper.dtoToTransaction(transactionRequest, Optional.of(bankAccount)));

        // Realizar la solicitud POST
        mockMvc.perform(post("/transactions/{accountId}/transactions", accountId)
                        .contentType("application/json")
                        .content("{ \"type\": \"deposit\", \"amount\": 100.0, \"date\": \"2024-12-16\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionId").value(1))
                .andExpect(jsonPath("$.type").value("deposit"))
                .andExpect(jsonPath("$.amount").value(100.0))
                .andExpect(jsonPath("$.date").value("2024-12-16"));

        // Verificar que los servicios fueron llamados correctamente
        verify(bankAccountService, times(1)).getBankAccount(accountId);
        verify(transactionsService, times(1)).createTransaction(any());
    }


}
