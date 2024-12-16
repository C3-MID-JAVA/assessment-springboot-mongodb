package com.kgalarza.bancointegrador.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.AccountMapper;
import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class AccountServiceTest {

    @Mock
    private AccountRepository cuentaRepository;

    @Mock
    private ClientRepository clienteRepository;

    @InjectMocks
    private AccountImplService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAccountSuccessfully() {
        // Arrange
        AccountInDto cuentaInDto = new AccountInDto();
        cuentaInDto.setNumeroCuenta("123456");
        cuentaInDto.setSaldo(new BigDecimal("100.00"));
        cuentaInDto.setClienteId("1");

        Client client = new Client();
        client.setId("1");
        client.setIdentification("1234567890");

        Account account = new Account();
        account.setId("1");
        account.setNumeroCuenta("123456");
        account.setSaldo(new BigDecimal("100.00"));
        account.setClienteId("1");

        AccountOutDto expectedDto = AccountMapper.toDto(account);

        when(clienteRepository.findById("1")).thenReturn(Optional.of(client));
        when(cuentaRepository.save(any(Account.class))).thenReturn(account);

        // Act
        AccountOutDto result = accountService.createAccount(cuentaInDto);

        // Assert
        assertNotNull(result);
        assertEquals("123456", result.getNumeroCuenta());
        assertEquals(new BigDecimal("100.00"), result.getSaldo());
        verify(clienteRepository, times(1)).findById("1");
        verify(cuentaRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnCreateAccount() {
        // Arrange
        AccountInDto cuentaInDto = new AccountInDto();
        cuentaInDto.setNumeroCuenta("123456");
        cuentaInDto.setSaldo(new BigDecimal("100.00"));
        cuentaInDto.setClienteId("1");

        when(clienteRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> accountService.createAccount(cuentaInDto));
        assertEquals("Cliente no encontrado con ID: 1", exception.getMessage());
        verify(clienteRepository, times(1)).findById("1");
        verify(cuentaRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllAccounts() {
        // Arrange
        List<Account> accounts = Arrays.asList(
                new Account("1", "123456", new BigDecimal("100.00"), "1", "1"),
                new Account("2", "654321", new BigDecimal("200.00"), "2", "2")
        );

        List<AccountOutDto> expectedDtos = accounts.stream().map(AccountMapper::toDto).collect(Collectors.toList());

        when(cuentaRepository.findAll()).thenReturn(accounts);

        // Act
        List<AccountOutDto> result = accountService.getAllAccounts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenNoAccountsFound() {
        // Arrange
        when(cuentaRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> accountService.getAllAccounts());
        assertEquals("No existen cuentas registradas.", exception.getMessage());
        verify(cuentaRepository, times(1)).findAll();
    }

    @Test
    void shouldGetAccountByIdSuccessfully() {
        // Arrange
        String id = "1";
        Account account = new Account("1", "123456", new BigDecimal("100.00"), "1", "1");
        AccountOutDto expectedDto = AccountMapper.toDto(account);

        when(cuentaRepository.findById(id)).thenReturn(Optional.of(account));

        // Act
        AccountOutDto result = accountService.getAccountById(id);

        // Assert
        assertNotNull(result);
        assertEquals("123456", result.getNumeroCuenta());
        verify(cuentaRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFound() {
        // Arrange
        String id = "999";
        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> accountService.getAccountById(id));
        assertEquals("Cuenta no encontrada con ID: 999", exception.getMessage());
        verify(cuentaRepository, times(1)).findById(id);
    }

    @Test
    void shouldUpdateAccountSuccessfully() {
        // Arrange
        String accountId = "1";
        AccountInDto cuentaInDto = new AccountInDto();
        cuentaInDto.setNumeroCuenta("111111");
        cuentaInDto.setSaldo(new BigDecimal("150.00"));
        Account existingAccount = new Account("1", "123456", new BigDecimal("100.00"), "1", "1");
        Account updatedAccount = new Account("1", "111111", new BigDecimal("150.00"), "1", "1");

        when(cuentaRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(cuentaRepository.save(any(Account.class))).thenReturn(updatedAccount);

        // Act
        AccountOutDto result = accountService.updateAccount(accountId, cuentaInDto);

        // Assert
        assertNotNull(result);
        assertEquals("111111", result.getNumeroCuenta());
        assertEquals(new BigDecimal("150.00"), result.getSaldo());
        verify(cuentaRepository, times(1)).findById(accountId);
        verify(cuentaRepository, times(1)).save(any(Account.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundOnUpdate() {
        // Arrange
        String id = "999";
        AccountInDto cuentaInDto = new AccountInDto();
        cuentaInDto.setNumeroCuenta("111111");
        cuentaInDto.setSaldo(new BigDecimal("150.00"));

        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> accountService.updateAccount(id, cuentaInDto));
        assertEquals("Cuenta no encontrada con ID: 999", exception.getMessage());
        verify(cuentaRepository, times(1)).findById(id);
        verify(cuentaRepository, never()).save(any());
    }

    @Test
    void shouldDeleteAccountSuccessfully() {
        // Arrange
        String id = "1";
        Account account = new Account();
        account.setId("1");
        when(cuentaRepository.findById(id)).thenReturn(Optional.of(account));

        // Act
        accountService.deleteAccount(id);

        // Assert
        verify(cuentaRepository, times(1)).delete(account);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundOnDelete() {
        // Arrange
        String id = "999";
        when(cuentaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> accountService.deleteAccount(id));
        assertEquals("Cuenta no encontrada con ID: 999", exception.getMessage());
        verify(cuentaRepository, never()).delete(any());
    }

}
