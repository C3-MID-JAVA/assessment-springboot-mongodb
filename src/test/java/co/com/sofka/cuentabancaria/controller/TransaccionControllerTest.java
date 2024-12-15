package co.com.sofka.cuentabancaria.controller;

import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.dto.util.PeticionByIdDTO;
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
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        TransaccionResponseDTO response = new TransaccionResponseDTO();

        when(transaccionService.realizarDeposito(request)).thenReturn(response);

        ResponseEntity<TransaccionResponseDTO> resultado = transaccionController.realizarDeposito(request);

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
        List<TransaccionResponseDTO> response = Arrays.asList(new TransaccionResponseDTO(), new TransaccionResponseDTO());

        when(transaccionService.obtenerTransacciones()).thenReturn(response);

        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.listarTransacciones();

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(2, resultado.getBody().size());
        verify(transaccionService, times(1)).obtenerTransacciones();
    }

    @Test
    void realizarRetiro_Exito() {
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        TransaccionResponseDTO response = new TransaccionResponseDTO();

        when(transaccionService.realizarRetiro(request)).thenReturn(response);

        ResponseEntity<TransaccionResponseDTO> resultado = transaccionController.realizarRetiro(request);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(response, resultado.getBody());
        verify(transaccionService, times(1)).realizarRetiro(request);
    }

    @Test
    void realizarRetiro_Fallo_Validacion() throws Exception {
        TransaccionRequestDTO request = new TransaccionRequestDTO();
        request.setNumeroCuenta(null);
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
        PeticionByIdDTO peticion = new PeticionByIdDTO("12345");
        List<TransaccionResponseDTO> response = Arrays.asList(new TransaccionResponseDTO(), new TransaccionResponseDTO());

        when(transaccionService.obtenerHistorialPorCuenta(peticion.getCuentaId())).thenReturn(response);

        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.obtenerHistorialPorCuenta(peticion);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(2, resultado.getBody().size());
        verify(transaccionService, times(1)).obtenerHistorialPorCuenta(peticion.getCuentaId());
    }

    @Test
    void obtenerHistorialPorCuenta_CuentaNoExiste() {
        PeticionByIdDTO peticion = new PeticionByIdDTO("cuenta_invalida");

        when(transaccionService.obtenerHistorialPorCuenta(peticion.getCuentaId())).thenReturn(List.of());

        ResponseEntity<List<TransaccionResponseDTO>> resultado = transaccionController.obtenerHistorialPorCuenta(peticion);

        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertTrue(resultado.getBody().isEmpty());
        verify(transaccionService, times(1)).obtenerHistorialPorCuenta(peticion.getCuentaId());
    }
}
