package com.kgalarza.bancointegrador.controller;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    void shouldCreateClientSuccessfully() {
        // Arrange
        ClientInDto clientInDto = new ClientInDto("1234567890", "John", "Doe", "john.doe@example.com", "+573001234567", "Main Street 123", LocalDate.of(1990, 5, 20));
        ClientOutDto clientOutDto = new ClientOutDto("1", "1234567890", "John", "Doe", "john.doe@example.com", "+573001234567", "Main Street 123", LocalDate.of(1990, 5, 20));

        when(clientService.saveClient(any(ClientInDto.class))).thenReturn(clientOutDto);

        // Act
        ResponseEntity<ClientOutDto> response = clientController.createClient(clientInDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
        assertEquals("John", response.getBody().getNombre());
    }


    @Test
    void shouldReturnAllClientsSuccessfully() {
        // Arrange
        List<ClientOutDto> clients = List.of(
                new ClientOutDto("1", "1234567890", "John", "Doe", "john.doe@example.com", "+573001234567", "Main Street 123", LocalDate.of(1990, 5, 20)),
                new ClientOutDto("2", "0987654321", "Jane", "Smith", "jane.smith@example.com", "+573009876543", "Second Avenue 456", LocalDate.of(1992, 3, 15))
        );

        when(clientService.getAll()).thenReturn(clients);

        // Act
        List<ClientOutDto> response = clientController.getClient();

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("John", response.get(0).getNombre());
        assertEquals("Jane", response.get(1).getNombre());
    }

    @Test
    void shouldReturnClientByIdSuccessfully() {
        // Arrange
        ClientOutDto clientOutDto = new ClientOutDto("1", "1234567890", "John", "Doe", "john.doe@example.com", "+573001234567", "Main Street 123", LocalDate.of(1990, 5, 20));
        when(clientService.getById("1")).thenReturn(clientOutDto);

        // Act
        ResponseEntity<ClientOutDto> response = clientController.getClienteById("1");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("1", response.getBody().getId());
    }

    @Test
    void shouldReturnNotFoundWhenClientByIdDoesNotExist() {
        // Arrange
        when(clientService.getById("1")).thenThrow(new ResourceNotFoundException("Client not found"));

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientController.getClienteById("1"));
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void shouldUpdateClientSuccessfully() {
        // Arrange
        ClientInDto clientInDto = new ClientInDto("1234567890", "John", "Doe Updated", "john.doe@example.com", "+573001234567", "Main Street 123",  LocalDate.of(1990, 5, 20));
        ClientOutDto updatedClient = new ClientOutDto("1", "1234567890", "John", "Doe Updated", "john.doe@example.com", "+573001234567", "Main Street 123", LocalDate.of(1990, 5, 20));

        when(clientService.updateClient(eq("1"), any(ClientInDto.class))).thenReturn(updatedClient);

        // Act
        ResponseEntity<ClientOutDto> response = clientController.updateClient("1", clientInDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Doe Updated", response.getBody().getApellido());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingClient() {
        // Arrange
        ClientInDto clientInDto = new ClientInDto("1234567890", "John", "Doe", "john.doe@example.com", "+573001234567", "Main Street 123",  LocalDate.of(1990, 5, 20));
        when(clientService.updateClient(eq("1"), any(ClientInDto.class)))
                .thenThrow(new ResourceNotFoundException("Client not found"));

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientController.updateClient("1", clientInDto));
        assertEquals("Client not found", exception.getMessage());
    }

    @Test
    void shouldDeleteClientSuccessfully() {
        // Arrange
        doNothing().when(clientService).deleteClient("1");

        // Act
        ResponseEntity<Void> response = clientController.deleteClient("1");

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingClient() {
        // Arrange
        doThrow(new ResourceNotFoundException("Client not found")).when(clientService).deleteClient("1");

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientController.deleteClient("1"));
        assertEquals("Client not found", exception.getMessage());
    }
}
