package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCuenta() {
        CuentaRequestDTO requestDTO = new CuentaRequestDTO("Cuenta 1", BigDecimal.valueOf(1000), "Anderson");
        CuentaResponseDTO responseDTO = new CuentaResponseDTO("1", "Cuenta 1", BigDecimal.valueOf(1000), "Zambrano");

        when(cuentaService.crearCuenta(requestDTO)).thenReturn(responseDTO);
        ResponseEntity<CuentaResponseDTO> response = cuentaController.crearCuenta(requestDTO);
        // Verificar resultados
        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(cuentaService, times(1)).crearCuenta(requestDTO);
    }

    @Test
    void testSaveCuentaDatosInvalidos() {
        // Crear un requestDTO con datos inválidos
        CuentaRequestDTO requestDTO = new CuentaRequestDTO(null, BigDecimal.valueOf(-1000), null);

        // Simular una excepción en el servicio
        when(cuentaService.crearCuenta(requestDTO)).thenThrow(new IllegalArgumentException("Datos inválidos"));

        // Llamar al método del controlador y capturar excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cuentaController.crearCuenta(requestDTO);
        });

        // Verificar resultados
        assertEquals("Datos inválidos", exception.getMessage());
        verify(cuentaService, times(1)).crearCuenta(requestDTO);
    }

    @Test
    void testObtenerCuentas() {
        // Mock de la lista de cuentas
        List<CuentaResponseDTO> cuentasMock = Arrays.asList(
                new CuentaResponseDTO("1", "Cuenta 1", BigDecimal.valueOf(1000), "Cristóbal"),
                new CuentaResponseDTO("2", "Cuenta 2", BigDecimal.valueOf(2000), "Balseca")
        );

        // Simular el comportamiento del servicio
        when(cuentaService.obtenerCuentas()).thenReturn(cuentasMock);

        // Llamar al método del controlador
        ResponseEntity<List<CuentaResponseDTO>> response = cuentaController.obtenerCuentas();

        // Verificar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(cuentasMock, response.getBody());
        verify(cuentaService, times(1)).obtenerCuentas();
    }

    @Test
    void testConsultarSaldoCuentaInexistente() {
        // Simular un comportamiento del servicio para una cuenta inexistente
        when(cuentaService.consultarSaldo("999")).thenThrow(new RuntimeException("Cuenta no encontrada"));

        // Llamar al método del controlador y capturar excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            cuentaController.consultarSaldo("999");
        });

        // Verificar resultados
        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaService, times(1)).consultarSaldo("999");
    }

    @Test
    void testObtenerCuentaPorId() {
        // Mock de la cuenta
        CuentaResponseDTO cuentaMock = new CuentaResponseDTO("1", "Cuenta 1", BigDecimal.valueOf(1000), "Cristhian");

        // Simular el comportamiento del servicio
        when(cuentaService.obtenerCuentaPorId("1")).thenReturn(cuentaMock);

        // Llamar al método del controlador
        ResponseEntity<CuentaResponseDTO> response = cuentaController.obtenerCuentaPorId("1");

        // Verificar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(cuentaMock, response.getBody());
        verify(cuentaService, times(1)).obtenerCuentaPorId("1");
    }

    @Test
    void testConsultarSaldo() {
        // Mock del saldo
        BigDecimal saldoMock = BigDecimal.valueOf(1500);

        // Simular el comportamiento del servicio
        when(cuentaService.consultarSaldo("1")).thenReturn(saldoMock);

        // Llamar al método del controlador
        ResponseEntity<BigDecimal> response = cuentaController.consultarSaldo("1");

        // Verificar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(saldoMock, response.getBody());
        verify(cuentaService, times(1)).consultarSaldo("1");
    }

    @Test
    void testObtenerCuentaPorIdInvalido() {
        // Simular una excepción para un ID inválido
        when(cuentaService.obtenerCuentaPorId("invalid_id")).thenThrow(new IllegalArgumentException("ID inválido"));

        // Llamar al método del controlador y capturar excepción
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cuentaController.obtenerCuentaPorId("invalid_id");
        });

        // Verificar resultados
        assertEquals("ID inválido", exception.getMessage());
        verify(cuentaService, times(1)).obtenerCuentaPorId("invalid_id");
    }

}
