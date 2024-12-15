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
        CuentaRequestDTO requestDTO = new CuentaRequestDTO("1234567890", BigDecimal.valueOf(1000), "Anderson");
        CuentaResponseDTO responseDTO = new CuentaResponseDTO("1", "1234567890", BigDecimal.valueOf(1000), "Zambrano");

        when(cuentaService.crearCuenta(requestDTO)).thenReturn(responseDTO);
        ResponseEntity<CuentaResponseDTO> response = cuentaController.crearCuenta(requestDTO);
        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(cuentaService, times(1)).crearCuenta(requestDTO);
    }

    @Test
    void testSaveCuentaDatosInvalidos() {
        CuentaRequestDTO requestDTO = new CuentaRequestDTO(null, BigDecimal.valueOf(-1000), null);

        when(cuentaService.crearCuenta(requestDTO)).thenThrow(new IllegalArgumentException("Datos inválidos"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cuentaController.crearCuenta(requestDTO);
        });

        assertEquals("Datos inválidos", exception.getMessage());
        verify(cuentaService, times(1)).crearCuenta(requestDTO);
    }

    @Test
    void testObtenerCuentas() {
        List<CuentaResponseDTO> cuentasMock = Arrays.asList(
                new CuentaResponseDTO("1", "1234567890", BigDecimal.valueOf(1000), "Cristóbal"),
                new CuentaResponseDTO("2", "1234567891", BigDecimal.valueOf(2000), "Balseca")
        );

        when(cuentaService.obtenerCuentas()).thenReturn(cuentasMock);

        ResponseEntity<List<CuentaResponseDTO>> response = cuentaController.obtenerCuentas();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(cuentasMock, response.getBody());
        verify(cuentaService, times(1)).obtenerCuentas();
    }

    @Test
    void testConsultarSaldoCuentaInexistente() {
        when(cuentaService.consultarSaldo("999")).thenThrow(new RuntimeException("Cuenta no encontrada"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            cuentaController.consultarSaldo("999");
        });

        assertEquals("Cuenta no encontrada", exception.getMessage());
        verify(cuentaService, times(1)).consultarSaldo("999");
    }

    @Test
    void testObtenerCuentaPorId() {
        CuentaResponseDTO cuentaMock = new CuentaResponseDTO("1", "1234567890", BigDecimal.valueOf(1000), "Cristhian");

        when(cuentaService.obtenerCuentaPorId("1")).thenReturn(cuentaMock);

        ResponseEntity<CuentaResponseDTO> response = cuentaController.obtenerCuentaPorId("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(cuentaMock, response.getBody());
        verify(cuentaService, times(1)).obtenerCuentaPorId("1");
    }

    @Test
    void testConsultarSaldo() {
        BigDecimal saldoMock = BigDecimal.valueOf(1500);

        when(cuentaService.consultarSaldo("1")).thenReturn(saldoMock);

        ResponseEntity<BigDecimal> response = cuentaController.consultarSaldo("1");

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(saldoMock, response.getBody());
        verify(cuentaService, times(1)).consultarSaldo("1");
    }

    @Test
    void testObtenerCuentaPorIdInvalido() {
        when(cuentaService.obtenerCuentaPorId("invalid_id")).thenThrow(new IllegalArgumentException("ID inválido"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            cuentaController.obtenerCuentaPorId("invalid_id");
        });

        assertEquals("ID inválido", exception.getMessage());
        verify(cuentaService, times(1)).obtenerCuentaPorId("invalid_id");
    }

}
