package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CuentaServiceIntegrationTest {

    @Autowired
    private CuentaServiceImpl cuentaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @BeforeEach
    void setUp() {
        cuentaRepository.deleteAll();
    }

    @Test
    void testCrearCuenta_Exitoso() {
        CuentaRequestDTO requestDTO = new CuentaRequestDTO("0123456789", BigDecimal.valueOf(1000), "Juan Perez");

        CuentaResponseDTO responseDTO = cuentaService.crearCuenta(requestDTO);

        Cuenta cuentaGuardada = cuentaRepository.findById(responseDTO.getId()).orElse(null);

        assertNotNull(cuentaGuardada);
        assertEquals("0123456789", cuentaGuardada.getNumeroCuenta());
        assertEquals(BigDecimal.valueOf(1000), cuentaGuardada.getSaldo());
    }

    @Test
    void testObtenerCuentaPorId_Exitoso() {
        Cuenta cuenta = new Cuenta("12345", BigDecimal.valueOf(500), "Juan Perez");
        cuentaRepository.save(cuenta);

        CuentaResponseDTO responseDTO = cuentaService.obtenerCuentaPorId(cuenta.getId());

        assertNotNull(responseDTO);
        assertEquals(cuenta.getNumeroCuenta(), responseDTO.getNumeroCuenta());
        assertEquals(cuenta.getTitular(), responseDTO.getTitular());
    }

    @Test
    void testObtenerCuentaPorId_NoExiste() {
        String cuentaIdInexistente = "99999";
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            cuentaService.obtenerCuentaPorId(cuentaIdInexistente);
        });

        assertEquals("No se encontro el cuenta con id: " + cuentaIdInexistente, thrown.getMessage());
    }



}
