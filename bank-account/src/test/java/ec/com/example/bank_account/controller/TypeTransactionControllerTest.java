package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.TypeTransactionRequestDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.service.TypeTransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TypeTransactionControllerTest {

    @Mock
    private TypeTransactionService typeTransactionService;

    @InjectMocks
    private TypeTransactionController typeTransactionController;

    @Test
    void createTypeTransaction_ShouldReturnCreatedStatus_WhenTransactionTypeIsCreatedSuccessfully() {
        TypeTransactionRequestDTO typeTransactionRequestDTO = new TypeTransactionRequestDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, "ACTIVE");
        TypeTransactionResponseDTO typeTransactionResponseDTO = new TypeTransactionResponseDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, "ACTIVE");
        when(typeTransactionService.createTypeTransaction(typeTransactionRequestDTO))
                .thenReturn(typeTransactionResponseDTO);

        ResponseEntity<TypeTransactionResponseDTO> response = typeTransactionController.createTypeTransaction(typeTransactionRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(typeTransactionResponseDTO, response.getBody());
        verify(typeTransactionService, times(1)).createTypeTransaction(typeTransactionRequestDTO);
    }

    @Test
    void createTypeTransaction_ShouldReturnBadRequest_WhenTransactionTypeIsNotCreated() {
        TypeTransactionRequestDTO typeTransactionRequestDTO = new TypeTransactionRequestDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, null);
        when(typeTransactionService.createTypeTransaction(typeTransactionRequestDTO))
                .thenReturn(null);

        ResponseEntity<TypeTransactionResponseDTO> response = typeTransactionController.createTypeTransaction(typeTransactionRequestDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(typeTransactionService, times(1)).createTypeTransaction(typeTransactionRequestDTO);
    }

    @Test
    void getAllTypeTransactions_ShouldReturnList_WhenTransactionsExist() {
        List<TypeTransactionResponseDTO> transactions = List.of(new TypeTransactionResponseDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, "ACTIVE"));
        when(typeTransactionService.getAllTypeTransactions()).thenReturn(transactions);

        ResponseEntity<List<TypeTransactionResponseDTO>> response = typeTransactionController.getAllTypeTransactions();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(typeTransactionService, times(1)).getAllTypeTransactions();
    }

    @Test
    void getAllTypeTransactions_ShouldReturnNoContent_WhenNoTransactionsExist() {
        when(typeTransactionService.getAllTypeTransactions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TypeTransactionResponseDTO>> response = typeTransactionController.getAllTypeTransactions();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(typeTransactionService, times(1)).getAllTypeTransactions();
    }
}
