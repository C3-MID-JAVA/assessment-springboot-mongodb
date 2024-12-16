package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.AccountRequestDTO;
import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private UserResponseDTO userResponse;
    private TypeAccountResponseDTO typeAccountResponse;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponseDTO("Diego", "Loor", "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
        typeAccountResponse = new TypeAccountResponseDTO("Debit account", "User debit account.", "ACTIVE");
    }

    @Test
    void createAccount_ShouldReturnCreatedStatus_WhenAccountIsCreatedSuccessfully() {
        AccountRequestDTO accountRequest = new AccountRequestDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", "test", "test");
        AccountResponseDTO accountResponse = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponse);
        when(accountService.createAccount(accountRequest)).thenReturn(accountResponse);

        ResponseEntity<AccountResponseDTO> response = accountController.createAccount(accountRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountResponse, response.getBody());
        verify(accountService, times(1)).createAccount(accountRequest);
    }

    @Test
    void createAccount_ShouldReturnBadRequest_WhenAccountServiceReturnsNull() {
        AccountRequestDTO accountRequest = new AccountRequestDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), null, "test", "test");
        when(accountService.createAccount(accountRequest)).thenReturn(null);

        ResponseEntity<AccountResponseDTO> response = accountController.createAccount(accountRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(accountService, times(1)).createAccount(accountRequest);
    }

    @Test
    void getAccounts_ShouldReturnOkStatus_WhenAccountsAreRetrievedSuccessfully() {
        AccountResponseDTO accountResponse = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponse);
        List<AccountResponseDTO> accounts = new ArrayList<>();
        accounts.add(accountResponse);
        when(accountService.getAllAccounts()).thenReturn(accounts);

        ResponseEntity<List<AccountResponseDTO>> response = accountController.getAccounts();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accounts, response.getBody());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void getAccounts_ShouldReturnNoContent_WhenNoAccountsAreFound() {
        when(accountService.getAllAccounts()).thenReturn(Collections.emptyList());

        ResponseEntity<List<AccountResponseDTO>> response = accountController.getAccounts();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(accountService, times(1)).getAllAccounts();
    }
}