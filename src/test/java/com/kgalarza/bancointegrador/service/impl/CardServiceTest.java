package com.kgalarza.bancointegrador.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.CardMapper;
import com.kgalarza.bancointegrador.model.dto.CardInDto;
import com.kgalarza.bancointegrador.model.dto.CardOutDto;
import com.kgalarza.bancointegrador.model.entity.Account;
import com.kgalarza.bancointegrador.model.entity.Card;
import com.kgalarza.bancointegrador.repository.AccountRepository;
import com.kgalarza.bancointegrador.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;

public class CardServiceTest {


    @Mock
    private CardRepository tarjetaRepository;

    @Mock
    private AccountRepository cuentaRepository;

    @InjectMocks
    private CardImplService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateCardSuccessfully() {
        // Arrange
        CardInDto tarjetaInDto = new CardInDto();
        tarjetaInDto.setNumeroTarjeta("1234-5678");
        tarjetaInDto.setTipo("Crédito");
        tarjetaInDto.setCuentaId("1");

        Account cuenta = new Account();
        cuenta.setId("1");
        cuenta.setNumeroCuenta("123456");

        Card tarjeta = new Card();
        tarjeta.setId("1");
        tarjeta.setNumeroTarjeta("1234-5678");
        tarjeta.setTipo("Crédito");
        tarjeta.setCuenta(cuenta);


        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(tarjetaRepository.save(any(Card.class))).thenReturn(tarjeta);

        // Act
        CardOutDto result = cardService.crearTarjeta(tarjetaInDto);

        // Assert
        assertNotNull(result);
        assertEquals("1234-5678", result.getNumeroTarjeta());
        assertEquals("Crédito", result.getTipo());
        verify(cuentaRepository, times(1)).findById("1");
        verify(tarjetaRepository, times(1)).save(any(Card.class));
    }

    @Test
    void shouldThrowExceptionWhenAccountNotFoundOnCreateCard() {
        // Arrange
        CardInDto tarjetaInDto = new CardInDto();
        tarjetaInDto.setNumeroTarjeta("1234-5678");
        tarjetaInDto.setTipo("Crédito");
        tarjetaInDto.setCuentaId("1");

        when(cuentaRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> cardService.crearTarjeta(tarjetaInDto));
        assertEquals("Cuenta no encontrada con ID: 1", exception.getMessage());
        verify(cuentaRepository, times(1)).findById("1");
        verify(tarjetaRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllCards() {
        // Arrange
        List<Card> tarjetas = Arrays.asList(
                new Card("1", "1234-5678", "Crédito"),
                new Card("2", "9876-5432", "Débito")
        );


        when(tarjetaRepository.findAll()).thenReturn(tarjetas);

        // Act
        List<CardOutDto> result = cardService.obtenerTodasLasTarjetas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(tarjetaRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenNoCardsFound() {
        // Arrange
        when(tarjetaRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> cardService.obtenerTodasLasTarjetas());
        assertEquals("No existen tarjetas registradas.", exception.getMessage());
        verify(tarjetaRepository, times(1)).findAll();
    }

    @Test
    void shouldGetCardByIdSuccessfully() {
        // Arrange
        String id = "1";
        Card tarjeta = new Card("1", "1234-5678", "Crédito");

        when(tarjetaRepository.findById(id)).thenReturn(Optional.of(tarjeta));

        // Act
        CardOutDto result = cardService.obtenerTarjetaPorId(id);

        // Assert
        assertNotNull(result);
        assertEquals("1234-5678", result.getNumeroTarjeta());
        verify(tarjetaRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenCardNotFound() {
        // Arrange
        String id = "999";
        when(tarjetaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> cardService.obtenerTarjetaPorId(id));
        assertEquals("Tarjeta no encontrada con ID: 999", exception.getMessage());
        verify(tarjetaRepository, times(1)).findById(id);
    }

    @Test
    void shouldUpdateCardSuccessfully() {
        // Arrange
        String id = "1";
        CardInDto tarjetaInDto = new CardInDto();
        tarjetaInDto.setNumeroTarjeta("5678-1234");
        tarjetaInDto.setTipo("Débito");
        tarjetaInDto.setCuentaId("1");

        Card tarjetaExistente = new Card("1", "1234-5678", "Crédito");
        Account cuenta = new Account();
        cuenta.setId("1");

        Card tarjetaActualizada = new Card("1", "5678-1234", "Débito");

        when(tarjetaRepository.findById(id)).thenReturn(Optional.of(tarjetaExistente));
        when(cuentaRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(tarjetaRepository.save(any(Card.class))).thenReturn(tarjetaActualizada);

        // Act
        CardOutDto result = cardService.actualizarTarjeta(id, tarjetaInDto);

        // Assert
        assertNotNull(result);
        assertEquals("5678-1234", result.getNumeroTarjeta());
        verify(tarjetaRepository, times(1)).save(any(Card.class));
        verify(cuentaRepository, times(1)).findById("1");
    }

    @Test
    void shouldThrowExceptionWhenCardNotFoundOnUpdate() {
        // Arrange
        String id = "999";
        CardInDto tarjetaInDto = new CardInDto();
        tarjetaInDto.setNumeroTarjeta("5678-1234");
        tarjetaInDto.setTipo("Débito");
        tarjetaInDto.setCuentaId("1");

        when(tarjetaRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> cardService.actualizarTarjeta(id, tarjetaInDto));
        assertEquals("Tarjeta no encontrada con ID: 999", exception.getMessage());
        verify(tarjetaRepository, never()).save(any());
    }

    @Test
    void shouldDeleteCardSuccessfully() {
        // Arrange
        String id = "1";
        when(tarjetaRepository.existsById(id)).thenReturn(true);

        // Act
        cardService.eliminarTarjeta(id);

        // Assert
        verify(tarjetaRepository, times(1)).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenCardNotFoundOnDelete() {
        // Arrange
        String id = "999";
        when(tarjetaRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> cardService.eliminarTarjeta(id));
        assertEquals("Tarjeta no encontrada con ID: 999", exception.getMessage());
    }


}
