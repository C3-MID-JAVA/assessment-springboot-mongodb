package co.com.sofka.cuentabancaria.service;

import static co.com.sofka.cuentabancaria.model.enums.TipoTransaccion.DEPOSITO_CAJERO;
import static co.com.sofka.cuentabancaria.model.enums.TipoTransaccion.RETIRO_CAJERO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.repository.TransaccionRepository;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategy;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategyFactory;
import co.com.sofka.cuentabancaria.service.strategy.enums.TipoOperacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;

public class TransaccionServiceUnitTest {

    private TransaccionServiceImpl transaccionService;
    private TransaccionRepository transaccionRepository;
    private CuentaRepository cuentaRepository;
    private TransaccionStrategyFactory strategyFactory;
    @BeforeEach
    void setUp() {
        transaccionRepository = mock(TransaccionRepository.class);
        cuentaRepository = mock(CuentaRepository.class);
        strategyFactory = mock(TransaccionStrategyFactory.class);
        transaccionService = new TransaccionServiceImpl(transaccionRepository, cuentaRepository, strategyFactory);
    }

    @Test
    void testRealizarDeposito_Exitoso() {
        TransaccionRequestDTO depositoRequestDTO = new TransaccionRequestDTO("12345", BigDecimal.valueOf(1000), DEPOSITO_CAJERO);

        Cuenta cuenta = new Cuenta("12345", BigDecimal.valueOf(2000), "Juan Perez");
        when(cuentaRepository.findByNumeroCuenta("12345")).thenReturn(Optional.of(cuenta));

        TransaccionStrategy strategy = mock(TransaccionStrategy.class);
        when(strategy.getCosto()).thenReturn(BigDecimal.valueOf(10));
        when(strategyFactory.getStrategy(DEPOSITO_CAJERO, TipoOperacion.DEPOSITO)).thenReturn(strategy);

        TransaccionResponseDTO responseDTO = transaccionService.realizarDeposito(depositoRequestDTO);

        assertEquals(BigDecimal.valueOf(2990), responseDTO.getNuevoSaldo());
    }

    @Test
    void testRealizarRetiro_Exitoso() {
        TransaccionRequestDTO retiroRequestDTO = new TransaccionRequestDTO("9876543210", BigDecimal.valueOf(1000),RETIRO_CAJERO);

        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(5000), "Juan Perez");
        when(cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta())).thenReturn(Optional.of(cuenta));

        TransaccionStrategy strategy = mock(TransaccionStrategy.class);
        when(strategy.getCosto()).thenReturn(BigDecimal.valueOf(100));
        when(strategyFactory.getStrategy(RETIRO_CAJERO, TipoOperacion.RETIRO)).thenReturn(strategy);

        TransaccionResponseDTO responseDTO = transaccionService.realizarRetiro(retiroRequestDTO);

        assertEquals(BigDecimal.valueOf(3900), responseDTO.getNuevoSaldo());
    }

}

