package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.TransactionRequestDTO;
import ec.com.example.bank_account.dto.TransactionResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @Mock
    private TransactionService transactionService;
    
    @InjectMocks
    private TransactionController transactionController;

    private AccountResponseDTO accountResponseDTO;
    private TypeTransactionResponseDTO typeTransactionResponseDTO;

    @BeforeEach
    void setUp() {
        UserResponseDTO userResponse = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
        TypeAccountResponseDTO typeAccountResponse = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");
        accountResponseDTO = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponse);

        typeTransactionResponseDTO = new TypeTransactionResponseDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, "ACTIVE");
    }

    @Test
    void createTransaction_ShouldReturnCreatedStatus_WhenTransactionIsCreatedSuccessfully() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(new BigDecimal(100), new Date(),
                "2200000000", "transaction made in Manabí.", "ACTIVE", "test");
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO(new BigDecimal(100), new Date(),
                "ACTIVE", accountResponseDTO, typeTransactionResponseDTO);
        when(transactionService.createTransaction(transactionRequest)).thenReturn(transactionResponse);

        ResponseEntity<TransactionResponseDTO> response = transactionController.createTransaction(transactionRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(transactionResponse, response.getBody());
        verify(transactionService, times(1)).createTransaction(transactionRequest);
    }

    @Test
    void createTransaction_ShouldReturnBadRequest_WhenTransactionServiceReturnsNull() {
        TransactionRequestDTO transactionRequest = new TransactionRequestDTO(new BigDecimal(100), new Date(),
                "2200000000", "transaction made in Manabí.", null, "test");
        when(transactionService.createTransaction(transactionRequest)).thenReturn(null);

        ResponseEntity<TransactionResponseDTO> response = transactionController.createTransaction(transactionRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(transactionService, times(1)).createTransaction(transactionRequest);
    }

    @Test
    void getAllTransactions_ShouldReturnOkStatus_WhenTransactionsAreRetrievedSuccessfully() {
        List<TransactionResponseDTO> transactions = List.of(new TransactionResponseDTO(new BigDecimal(100), new Date(),
                "ACTIVE", accountResponseDTO, typeTransactionResponseDTO));
        when(transactionService.getAllTransactions()).thenReturn(transactions);

        ResponseEntity<List<TransactionResponseDTO>> response = transactionController.getTransactions();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactions, response.getBody());
        verify(transactionService, times(1)).getAllTransactions();
    }

    @Test
    void getAllTransactions_ShouldReturnNoContent_WhenNoTransactionsAreFound() {
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TransactionResponseDTO>> response = transactionController.getTransactions();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(transactionService, times(1)).getAllTransactions();
    }
    
}
