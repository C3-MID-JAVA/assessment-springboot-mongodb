package com.kgalarza.bancointegrador.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.ClientMapper;
import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

public class ClientServiceTest {

    @InjectMocks
    private ClientImplService clientService;

    @Mock
    private ClientRepository clienteRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveClientSuccessfully() {
        // Arrange
        ClientInDto clientInDto = new ClientInDto(
                "1234567890",
                "John",
                "Doe",
                "john.doe@example.com",
                "+521234567890",
                "123 Elm Street",
                LocalDate.of(1990, 5, 20)
        );

        Client clientEntity = new Client(
                "1",
                clientInDto.getIdentificacion(),
                clientInDto.getNombre(),
                clientInDto.getApellido(),
                clientInDto.getEmail(),
                clientInDto.getTelefono(),
                clientInDto.getDireccion(),
                LocalDate.of(1990, 5, 20)
        );

        when(clienteRepository.save(any(Client.class))).thenReturn(clientEntity);

        // Act
        ClientOutDto result = clientService.saveClient(clientInDto);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals("john.doe@example.com", result.getEmail());
        verify(clienteRepository, times(1)).save(any(Client.class));
    }

    // Test para obtener todos los clientes
    @Test
    void shouldReturnAllClientsWhenClientsExist() {
        // Arrange
        List<Client> clients = Arrays.asList(
                new Client("1", "1234567890", "John", "Doe", "john.doe@example.com", "+521234567890", "123 Elm Street", LocalDate.of(1990, 5, 20)),
                new Client("2", "0987654321", "Jane", "Smith", "jane.smith@example.com", "+529876543210", "456 Pine Street", LocalDate.of(1995, 6, 15))
        );

        when(clienteRepository.findAll()).thenReturn(clients);

        // Act
        List<ClientOutDto> result = clientService.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void shouldThrowExceptionWhenNoClientsExist() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientService.getAll());
        assertEquals("No existen clientes registrados.", exception.getMessage());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void shouldFindClientById() {
        // Arrange
        String clientId = "1";
        Client clientEntity = new Client(
                "1",
                "1234567890",
                "John",
                "Doe",
                "john.doe@example.com",
                "+521234567890",
                "123 Elm Street",
                LocalDate.of(1990, 5, 20)
        );

        when(clienteRepository.findById(clientId)).thenReturn(Optional.of(clientEntity));

        // Act
        ClientOutDto result = clientService.getById(clientId);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        verify(clienteRepository, times(1)).findById(clientId);
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        // Arrange
        String clientId = "999";
        when(clienteRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientService.getById(clientId));
        assertEquals("Cliente no encontrado con ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clientId);
    }

    @Test
    void shouldDeleteClientSuccessfully() {
        // Arrange
        String clientId = "1";
        when(clienteRepository.existsById(clientId)).thenReturn(true);

        doNothing().when(clienteRepository).deleteById(clientId);

        // Act
        clientService.deleteClient(clientId);

        // Assert
        verify(clienteRepository, times(1)).existsById(clientId);
        verify(clienteRepository, times(1)).deleteById(clientId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentClient() {
        // Arrange
        String clientId = "999";
        when(clienteRepository.existsById(clientId)).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientService.deleteClient(clientId));
        assertEquals("Cliente no encontrado con ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).existsById(clientId);
    }

    @Test
    void shouldUpdateClientSuccessfully() {
        // Arrange
        String clientId = "1";
        ClientInDto clientInDto = new ClientInDto(
                "1234567890",
                "John",
                "Doe",
                "new.email@example.com",
                "+521234567890",
                "123 Elm Street",
                LocalDate.of(1990, 5, 20)
        );

        Client existingClient = new Client(
                "1",
                "1234567890",
                "OldFirstName",
                "OldLastName",
                "old.email@example.com",
                "+521234567890",
                "456 Old Street",
                LocalDate.of(1985, 1, 1)
        );

        Client updatedClientEntity = new Client(
                "1",
                clientInDto.getIdentificacion(),
                clientInDto.getNombre(),
                clientInDto.getApellido(),
                clientInDto.getEmail(),
                clientInDto.getTelefono(),
                clientInDto.getDireccion(),
                LocalDate.of(1990, 5, 20)
        );

        ClientOutDto expectedDto = ClientMapper.toDto(updatedClientEntity);

        when(clienteRepository.findById(clientId)).thenReturn(Optional.of(existingClient));
        when(clienteRepository.save(any(Client.class))).thenReturn(updatedClientEntity);

        // Act
        ClientOutDto result = clientService.updateClient(clientId, clientInDto);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNombre());
        assertEquals("Doe", result.getApellido());
        assertEquals("new.email@example.com", result.getEmail());
        verify(clienteRepository, times(1)).findById(clientId);
        verify(clienteRepository, times(1)).save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenClientNotFoundOnUpdate() {
        // Arrange
        String clientId = "999";
        ClientInDto clientInDto = new ClientInDto(
                "1234567890",
                "John",
                "Doe",
                "new.email@example.com",
                "+521234567890",
                "123 Elm Street",
                LocalDate.of(1990, 5, 20)
        );

        when(clienteRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(clientId, clientInDto));
        assertEquals("Cliente no encontrado con ID: 999", exception.getMessage());
        verify(clienteRepository, times(1)).findById(clientId);
        verify(clienteRepository, never()).save(any());
    }

}
