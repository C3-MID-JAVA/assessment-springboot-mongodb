package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTest {
    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        // Arrange
        AccountInDto accountInDto = new AccountInDto("1234567890", new BigDecimal("1000.00"), "cliente1");
        AccountOutDto accountOutDto = new AccountOutDto("1", "1234567890", new BigDecimal("1000.00"));

        // Mock del comportamiento esperado
        when(accountService.createAccount(accountInDto)).thenReturn(accountOutDto);

        // Act
        ResponseEntity<AccountOutDto> response = accountController.createAccount(accountInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(accountOutDto.getId(), response.getBody().getId());
        assertEquals(accountOutDto.getNumeroCuenta(), response.getBody().getNumeroCuenta());
        assertEquals(accountOutDto.getSaldo(), response.getBody().getSaldo());
        verify(accountService, times(1)).createAccount(accountInDto);
    }

    @Test
    void shouldReturnBadRequestWhenCreatingAccountWithInvalidData() {
        // Arrange
        AccountInDto invalidAccountInDto = new AccountInDto(null, new BigDecimal("-100"), null);

        when(accountService.createAccount(invalidAccountInDto)).thenThrow(new IllegalArgumentException("Datos inválidos"));

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            accountController.createAccount(invalidAccountInDto);
        });

        assertEquals("Datos inválidos", exception.getMessage());
        verify(accountService, times(1)).createAccount(invalidAccountInDto);
    }

    @Test
    void shouldReturnAllAccountsSuccessfully() {
        // Arrange
        List<AccountOutDto> cuentas = List.of(
                new AccountOutDto("1", "1234567890", new BigDecimal("1000.00")),
                new AccountOutDto("2", "9876543210", new BigDecimal("2000.00"))
        );

        when(accountService.getAllAccounts()).thenReturn(cuentas);

        // Act
        ResponseEntity<List<AccountOutDto>> response = accountController.getAllAccounts();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void shouldUpdateAccountSuccessfully() {
        // Arrange
        String accountId = "1";
        AccountInDto accountInDto = new AccountInDto("9876543210", new BigDecimal("1500.00"), "cliente2");
        AccountOutDto updatedAccount = new AccountOutDto(accountId, "9876543210", new BigDecimal("1500.00"));

        when(accountService.updateAccount(accountId, accountInDto)).thenReturn(updatedAccount);

        // Act
        ResponseEntity<AccountOutDto> response = accountController.updateAccount(accountId, accountInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedAccount.getNumeroCuenta(), response.getBody().getNumeroCuenta());
        verify(accountService, times(1)).updateAccount(accountId, accountInDto);
    }

    @Test
    void shouldDeleteAccountSuccessfully() {
        // Arrange
        doNothing().when(accountService).deleteAccount("1");

        // Act
        ResponseEntity<Void> response = accountController.deleteAccount("1");

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(accountService, times(1)).deleteAccount("1");
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingAccount() {
        // Arrange
        doThrow(new ResourceNotFoundException("Account not found")).when(accountService).deleteAccount("1");

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountController.deleteAccount("1");
        });

        assertEquals("Account not found", exception.getMessage());
        verify(accountService, times(1)).deleteAccount("1");
    }

    @Test
    void shouldGetAccountByIdSuccessfully() {
        // Arrange
        String accountId = "1";
        AccountOutDto accountOutDto = new AccountOutDto(accountId, "1234567890", new BigDecimal("1000.00"));

        when(accountService.getAccountById(accountId)).thenReturn(accountOutDto);

        // Act
        ResponseEntity<AccountOutDto> response = accountController.getAccountById(accountId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(accountOutDto.getId(), response.getBody().getId());
        assertEquals(accountOutDto.getNumeroCuenta(), response.getBody().getNumeroCuenta());
        verify(accountService, times(1)).getAccountById(accountId);
    }
}
