package com.kgalarza.bancointegrador.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Transaction;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository movimientoRepository;

    @Mock
    private AccountRepository cuentaRepository;

    @InjectMocks
    private TransactionImplService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundOnDeposit() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Depósito sucursal");
        movimientoInDto.setMonto((double) 100);
        movimientoInDto.setCuentaId("999");

        when(cuentaRepository.findById("999")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                transactionService.makeBranchDeposit(movimientoInDto)
        );
        assertEquals("Cuenta no encontrada con ID: 999", exception.getMessage());
    }

    @Test
    void shouldHandleATMDepositCorrectly() {
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Depósito Cajero");
        movimientoInDto.setMonto(50.0);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("100"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Depósito Cajero");
        transaction.setMonto(new BigDecimal("45.0"));
        transaction.setTipoMovimiento("crédito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionOutDto result = transactionService.makeATMDeposit(movimientoInDto);

        assertNotNull(result);
        assertEquals("Depósito Cajero", result.getDescripcion());
        assertEquals(new BigDecimal("48"), result.getMonto());
        assertEquals("crédito", result.getTipoMovimiento());
    }

    @Test
    void shouldHandleTransactionWithInsufficientFunds() {
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Retirar efectivo");
        movimientoInDto.setMonto((double)1000);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("100.0"));

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                transactionService.makeATMWithdrawal(movimientoInDto)
        );

        assertEquals("Saldo insuficiente para realizar la transacción.", exception.getMessage());
    }

    @Test
    void shouldMakeOnlinePurchaseCorrectly() {
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Compra online");
        movimientoInDto.setMonto((double)100);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("200.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Compra online");
        transaction.setMonto(new BigDecimal("95.0"));
        transaction.setTipoMovimiento("débito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionOutDto result = transactionService.makeOnlinePurchase(movimientoInDto);

        assertNotNull(result);
        assertEquals("Compra online", result.getDescripcion());
        assertEquals(new BigDecimal("105"), result.getMonto());
        assertEquals("débito", result.getTipoMovimiento());
    }

    @Test
    void shouldThrowExceptionIfTransactionFailsDueToInsufficientBalance() {
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Compra");
        movimientoInDto.setMonto((double)200);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("50"));

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));

        Exception exception = assertThrows(ResourceNotFoundException.class, () ->
                transactionService.makePhysicalPurchase(movimientoInDto)
        );

        assertEquals("Saldo insuficiente para realizar la transacción.", exception.getMessage());
    }


    @Test
    void shouldMakeBranchDepositSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Depósito sucursal");
        movimientoInDto.setMonto((double)100);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("200.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Depósito sucursal");
        transaction.setMonto(new BigDecimal("100.0"));
        transaction.setTipoMovimiento("crédito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutDto result = transactionService.makeBranchDeposit(movimientoInDto);

        // Assert
        assertNotNull(result);
        assertEquals("Depósito sucursal", result.getDescripcion());
        assertEquals(new BigDecimal("100"), result.getMonto());
        assertEquals("crédito", result.getTipoMovimiento());
    }

    @Test
    void shouldMakeATMDepositSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Depósito Cajero");
        movimientoInDto.setMonto((double)100);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("200.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Depósito Cajero");
        transaction.setMonto(new BigDecimal("98.0")); // 100 - 2 (costo)
        transaction.setTipoMovimiento("crédito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutDto result = transactionService.makeATMDeposit(movimientoInDto);

        // Assert
        assertNotNull(result);
        assertEquals("Depósito Cajero", result.getDescripcion());
        assertEquals(new BigDecimal("98"), result.getMonto());
        assertEquals("crédito", result.getTipoMovimiento());
    }

    @Test
    void shouldMakeDepositToAnotherAccountSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Depósito otra cuenta");
        movimientoInDto.setMonto((double)100);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("200.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Depósito otra cuenta");
        transaction.setMonto(new BigDecimal("98.5")); // 100 - 1.5
        transaction.setTipoMovimiento("crédito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutDto result = transactionService.makeDepositToAnotherAccount(movimientoInDto);

        // Assert
        assertNotNull(result);
        assertEquals("Depósito otra cuenta", result.getDescripcion());
        assertEquals(new BigDecimal("98.5"), result.getMonto());
        assertEquals("crédito", result.getTipoMovimiento());
    }

    @Test
    void shouldMakePhysicalPurchaseSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Compra física");
        movimientoInDto.setMonto((double)50);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("200.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Compra física");
        transaction.setMonto(new BigDecimal("50.0"));
        transaction.setTipoMovimiento("débito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutDto result = transactionService.makePhysicalPurchase(movimientoInDto);

        // Assert
        assertNotNull(result);
        assertEquals("Compra física", result.getDescripcion());
        assertEquals(new BigDecimal("50"), result.getMonto());
        assertEquals("débito", result.getTipoMovimiento());
    }

    @Test
    void shouldMakeATMWithdrawalSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto();
        movimientoInDto.setDescripcion("Retiro ATM");
        movimientoInDto.setMonto((double)30);
        movimientoInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setSaldo(new BigDecimal("100.0"));

        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setDescripcion("Retiro ATM");
        transaction.setMonto(new BigDecimal("29")); // 30 - 1
        transaction.setTipoMovimiento("débito");
        transaction.setFecha(LocalDate.now());

        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(movimientoRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Act
        TransactionOutDto result = transactionService.makeATMWithdrawal(movimientoInDto);

        // Assert
        assertNotNull(result);
        assertEquals("Retiro ATM", result.getDescripcion());
        assertEquals(new BigDecimal("31"), result.getMonto());
        assertEquals("débito", result.getTipoMovimiento());
    }

}
