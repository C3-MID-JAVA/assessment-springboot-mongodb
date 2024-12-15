package co.com.sofka.cuentabancaria.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.config.exceptions.ConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CuentaServiceUnitTest {

    private CuentaServiceImpl cuentaService;
    private CuentaRepository cuentaRepository;

    @BeforeEach
    void setUp() {
        cuentaRepository = mock(CuentaRepository.class);
        cuentaService = new CuentaServiceImpl(cuentaRepository);
    }

    @Test
    void testCrearCuenta_Exitoso() {
        CuentaRequestDTO requestDTO = new CuentaRequestDTO("0123456789", BigDecimal.valueOf(1000), "Juan Perez");
        Cuenta cuenta = new Cuenta("675dbabe03edcf54111957fe","12345", BigDecimal.valueOf(1000), "Juan Perez");

        when(cuentaRepository.findByNumeroCuenta("12345")).thenReturn(Optional.empty());
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuenta);

        CuentaResponseDTO responseDTO = cuentaService.crearCuenta(requestDTO);

        assertEquals("12345", responseDTO.getNumeroCuenta());
        assertEquals(BigDecimal.valueOf(1000), responseDTO.getSaldo());
        verify(cuentaRepository, times(1)).save(any(Cuenta.class));
    }


    @Test
    void testCrearCuenta_CuentaExistente() {
        CuentaRequestDTO cuentaRequestDTO = new CuentaRequestDTO("0123456789", BigDecimal.valueOf(1000), "Juan Perez");

        when(cuentaRepository.findByNumeroCuenta(cuentaRequestDTO.getNumeroCuenta())).thenReturn(Optional.of(new Cuenta()));

        ConflictException exception = assertThrows(ConflictException.class, () -> cuentaService.crearCuenta(cuentaRequestDTO));
        assertEquals("El número de cuenta ya está registrado.", exception.getMessage());
    }

    @Test
    void testObtenerCuentaPorId_Exitoso() {
        Cuenta cuenta = new Cuenta("675dbabe03edcf54111957fe","0123456789", BigDecimal.valueOf(1000), "Juan Perez");

        when(cuentaRepository.findById("675dbabe03edcf54111957fe")).thenReturn(Optional.of(cuenta));

        CuentaResponseDTO responseDTO = cuentaService.obtenerCuentaPorId("675dbabe03edcf54111957fe");

        assertEquals("0123456789", responseDTO.getNumeroCuenta());
        assertEquals("Juan Perez", responseDTO.getTitular());
        verify(cuentaRepository, times(1)).findById("675dbabe03edcf54111957fe");
    }


    @Test
    void testObtenerCuentaPorId_NoExiste() {
        when(cuentaRepository.findById("12345")).thenReturn(Optional.empty());

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            cuentaService.obtenerCuentaPorId("12345");
        });

        assertEquals("No se encontro el cuenta con id: 12345", thrown.getMessage());
    }

    @Test
    void testCrearCuenta_CuentaNueva() {
        CuentaRequestDTO cuentaRequestDTO = new CuentaRequestDTO("123456", BigDecimal.valueOf(1000),"Juan Pérez");
        Cuenta cuentaMock = new Cuenta();
        cuentaMock.setId("1");
        cuentaMock.setNumeroCuenta(cuentaRequestDTO.getNumeroCuenta());
        cuentaMock.setTitular(cuentaRequestDTO.getTitular());
        cuentaMock.setSaldo(cuentaRequestDTO.getSaldoInicial());

        when(cuentaRepository.findByNumeroCuenta(cuentaRequestDTO.getNumeroCuenta())).thenReturn(Optional.empty());
        when(cuentaRepository.save(any(Cuenta.class))).thenReturn(cuentaMock);

        CuentaResponseDTO cuentaResponseDTO = cuentaService.crearCuenta(cuentaRequestDTO);

        assertNotNull(cuentaResponseDTO);
        assertEquals("1", cuentaResponseDTO.getId());
        assertEquals("123456", cuentaResponseDTO.getNumeroCuenta());
    }

}
