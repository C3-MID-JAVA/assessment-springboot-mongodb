package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import com.kgalarza.bancointegrador.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CardControllerTest {

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService tarjetaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCardSuccessfully() {
        // Arrange
        CardInDto tarjetaInDto = new CardInDto("1234567890123456", "Credito", "12/25", "1");
        CardOutDto tarjetaOutDto = new CardOutDto("1", "1234567890123456", "Credito", "1");

        when(tarjetaService.crearTarjeta(tarjetaInDto)).thenReturn(tarjetaOutDto);

        // Act
        ResponseEntity<CardOutDto> response = cardController.crearTarjeta(tarjetaInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tarjetaOutDto.getId(), response.getBody().getId());
        assertEquals(tarjetaOutDto.getNumeroTarjeta(), response.getBody().getNumeroTarjeta());
        assertEquals(tarjetaOutDto.getTipo(), response.getBody().getTipo());
        assertEquals(tarjetaOutDto.getCuentaId(), response.getBody().getCuentaId());
        verify(tarjetaService, times(1)).crearTarjeta(tarjetaInDto);
    }

    @Test
    void shouldReturnAllCardsSuccessfully() {
        // Arrange
        List<CardOutDto> tarjetas = List.of(
                new CardOutDto("1", "1234567890123456", "Credito", "1"),
                new CardOutDto("2", "9876543210987654", "Debito", "2")
        );

        when(tarjetaService.obtenerTodasLasTarjetas()).thenReturn(tarjetas);

        // Act
        ResponseEntity<List<CardOutDto>> response = cardController.obtenerTodasLasTarjetas();

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(tarjetaService, times(1)).obtenerTodasLasTarjetas();
    }

    @Test
    void shouldGetCardByIdSuccessfully() {
        // Arrange
        String cardId = "1";
        CardOutDto tarjetaOutDto = new CardOutDto(cardId, "1234567890123456", "Credito", "1");

        when(tarjetaService.obtenerTarjetaPorId(cardId)).thenReturn(tarjetaOutDto);

        // Act
        ResponseEntity<CardOutDto> response = cardController.obtenerTarjetaPorId(cardId);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tarjetaOutDto.getId(), response.getBody().getId());
        assertEquals(tarjetaOutDto.getTipo(), response.getBody().getTipo());
        verify(tarjetaService, times(1)).obtenerTarjetaPorId(cardId);
    }

    @Test
    void shouldUpdateCardSuccessfully() {
        // Arrange
        String cardId = "1";
        CardInDto tarjetaInDto = new CardInDto("9876543210987654", "Debito", "01/26", "2");
        CardOutDto tarjetaActualizada = new CardOutDto(cardId, "9876543210987654", "Debito", "2");

        when(tarjetaService.actualizarTarjeta(cardId, tarjetaInDto)).thenReturn(tarjetaActualizada);

        // Act
        ResponseEntity<CardOutDto> response = cardController.actualizarTarjeta(cardId, tarjetaInDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(tarjetaActualizada.getTipo(), response.getBody().getTipo());
        verify(tarjetaService, times(1)).actualizarTarjeta(cardId, tarjetaInDto);
    }

    @Test
    void shouldDeleteCardSuccessfully() {
        // Arrange
        doNothing().when(tarjetaService).eliminarTarjeta("1");

        // Act
        ResponseEntity<Void> response = cardController.eliminarTarjeta("1");

        // Assert
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        verify(tarjetaService, times(1)).eliminarTarjeta("1");
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingCard() {
        // Arrange
        doThrow(new ResourceNotFoundException("Tarjeta no encontrada")).when(tarjetaService).eliminarTarjeta("1");

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cardController.eliminarTarjeta("1");
        });

        assertEquals("Tarjeta no encontrada", exception.getMessage());
        verify(tarjetaService, times(1)).eliminarTarjeta("1");
    }

}
