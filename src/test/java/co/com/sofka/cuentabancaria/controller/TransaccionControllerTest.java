package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.service.iservice.TransaccionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarDeposito_Exito() {
        // Datos de prueba
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        TransaccionResponseDTO response = new TransaccionResponseDTO();

        when(transaccionService.realizarDeposito(request)).thenReturn(response);

        // Ejecución
        ResponseEntity<TransaccionResponseDTO> resultado = transaccionController.realizarDeposito(request);

        // Validaciones
        assertEquals(HttpStatus.CREATED, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        verify(transaccionService, times(1)).realizarDeposito(request);
    }

    @Test
    void realizarDeposito_Fallo_Validacion() {
        TransaccionRequestDTO request = null;

        when(transaccionService.realizarDeposito(request))
                .thenThrow(new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo"));

        assertThrows(IllegalArgumentException.class, () -> transaccionController.realizarDeposito(request));
        verify(transaccionService, times(1)).realizarDeposito(request);
    }


    @Test
    void listarTransacciones_Exito() {
        // Datos de prueba
        List<TransaccionResponseDTO> response = Arrays.asList(new TransaccionResponseDTO(), new TransaccionResponseDTO());

        when(transaccionService.obtenerTransacciones()).thenReturn(response);

        // Ejecución
        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.listarTransacciones();

        // Validaciones
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(2, resultado.getBody().size());
        verify(transaccionService, times(1)).obtenerTransacciones();
    }

    @Test
    void realizarRetiro_Exito() {
        // Datos de prueba
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        TransaccionResponseDTO response = new TransaccionResponseDTO();

        when(transaccionService.realizarRetiro(request)).thenReturn(response);

        // Ejecución
        ResponseEntity<TransaccionResponseDTO> resultado = transaccionController.realizarRetiro(request);

        // Validaciones
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        verify(transaccionService, times(1)).realizarRetiro(request);
    }

    @Test
    void realizarRetiro_Fallo_Validacion() throws Exception {
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        request.setCuentaId(null);
        request.setMonto(null);
        request.setTipoTransaccion(null);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(transaccionController).build();


        mockMvc.perform(MockMvcRequestBuilders.post("/transacciones/retiro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());


        verify(transaccionService, times(0)).realizarRetiro(any());
    }

    @Test
    void obtenerHistorialPorCuenta_Exito() {
        // Datos de prueba
        String cuentaId = "12345";
        List<TransaccionResponseDTO> response = Arrays.asList(new TransaccionResponseDTO(), new TransaccionResponseDTO());

        when(transaccionService.obtenerHistorialPorCuenta(cuentaId)).thenReturn(response);

        // Ejecución
        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.obtenerHistorialPorCuenta(cuentaId);

        // Validaciones
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(2, resultado.getBody().size());
        verify(transaccionService, times(1)).obtenerHistorialPorCuenta(cuentaId);
    }

    @Test
    void obtenerHistorialPorCuenta_CuentaNoExiste() {
        // Datos de prueba
        String cuentaId = "cuenta_invalida";
        when(transaccionService.obtenerHistorialPorCuenta(cuentaId)).thenReturn(List.of());

        // Ejecución
        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.obtenerHistorialPorCuenta(cuentaId);

        // Validaciones
        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(transaccionService, times(1)).obtenerHistorialPorCuenta(cuentaId);
    }
}
