package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.model.dto.TransactionInDto;
import com.kgalarza.bancointegrador.model.dto.TransactionOutDto;
import com.kgalarza.bancointegrador.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService movimientoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMakeBranchDepositSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto("depósito sucrusal", 10.0, "675e8ab6ecc70f5dc45c4bcc");
        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "1",
                "depósito sucrusal",
                new BigDecimal("10.0"),
                "Sucursal",
                LocalDate.now()
        );

        when(movimientoService.makeBranchDeposit(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarDepositoSucursal(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("depósito sucrusal", response.getBody().getDescripcion());
        assertEquals(10.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makeBranchDeposit(movimientoInDto);
    }

    @Test
    void shouldMakeATMDepositSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto("depósito cajero", 20.0, "675e8ab6ecc70f5dc45c4bcc");
        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "2",
                "depósito cajero",
                new BigDecimal("20.0"),
                "Cajero",
                LocalDate.now()
        );

        when(movimientoService.makeATMDeposit(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarDepositoCajero(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("depósito cajero", response.getBody().getDescripcion());
        assertEquals(20.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makeATMDeposit(movimientoInDto);
    }

    @Test
    void shouldHandleDepositToAnotherAccountSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto("depósito otra cuenta", 15.0, "675e8ab6ecc70f5dc45c4bcc");
        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "3",
                "depósito otra cuenta",
                new BigDecimal("15.0"),
                "Cuenta",
                LocalDate.now()
        );

        when(movimientoService.makeDepositToAnotherAccount(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarDepositoOtraCuenta(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("depósito otra cuenta", response.getBody().getDescripcion());
        assertEquals(15.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makeDepositToAnotherAccount(movimientoInDto);
    }

    @Test
    void shouldHandlePhysicalPurchaseSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto("compra en tienda", 30.0, "675e8ab6ecc70f5dc45c4bcc");
        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "4",
                "compra en tienda",
                new BigDecimal("30.0"),
                "Compra Física",
                LocalDate.now()
        );

        when(movimientoService.makePhysicalPurchase(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarCompraFisica(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("compra en tienda", response.getBody().getDescripcion());
        assertEquals(30.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makePhysicalPurchase(movimientoInDto);
    }

    @Test
    void shouldHandleATMWithdrawalSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto("retiro cajero", 40.0, "675e8ab6ecc70f5dc45c4bcc");
        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "5",
                "retiro cajero",
                new BigDecimal("40.0"),
                "Cajero",
                LocalDate.now()
        );

        when(movimientoService.makeATMWithdrawal(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarRetiroCajero(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("retiro cajero", response.getBody().getDescripcion());
        assertEquals(40.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makeATMWithdrawal(movimientoInDto);
    }

    @Test
    void shouldHandleOnlinePurchaseSuccessfully() {
        // Arrange
        TransactionInDto movimientoInDto = new TransactionInDto(
                "compra online",
                50.0,
                "675e8ab6ecc70f5dc45c4bcc"
        );

        TransactionOutDto movimientoOutDto = new TransactionOutDto(
                "6",
                "compra online",
                new BigDecimal("50.0"),
                "Compra Web",
                LocalDate.now()
        );

        // Configurar la respuesta esperada para la operación en el servicio
        when(movimientoService.makeOnlinePurchase(movimientoInDto)).thenReturn(movimientoOutDto);

        // Act
        ResponseEntity<TransactionOutDto> response = transactionController.realizarCompraWeb(movimientoInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("compra online", response.getBody().getDescripcion());
        assertEquals(50.0, response.getBody().getMonto().doubleValue());
        verify(movimientoService, times(1)).makeOnlinePurchase(movimientoInDto);
    }
}
