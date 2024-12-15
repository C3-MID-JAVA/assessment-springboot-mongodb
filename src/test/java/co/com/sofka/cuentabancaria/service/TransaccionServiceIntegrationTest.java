package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.repository.TransaccionRepository;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategy;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategyFactory;
import co.com.sofka.cuentabancaria.service.strategy.enums.TipoOperacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TransaccionServiceIntegrationTest {


    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionServiceImpl transaccionService;

    @Autowired
    public TransaccionServiceIntegrationTest(CuentaRepository cuentaRepository,
                                             TransaccionServiceImpl transaccionService,
                                             TransaccionRepository transaccionRepository) {
        this.cuentaRepository = cuentaRepository;
        this.transaccionService = transaccionService;
        this.transaccionRepository = transaccionRepository;
    }

    @Mock
    private TransaccionStrategyFactory strategyFactory;


    @BeforeEach
    void setUp() {
        cuentaRepository.deleteAll();
        transaccionRepository.deleteAll();
    }

    @Test
    void testRealizarDeposito_Exitoso() {
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(100000), "Juan Perez");
        cuentaRepository.save(cuenta);

        TransaccionRequestDTO depositoRequestDTO = new TransaccionRequestDTO(cuenta.getNumeroCuenta(), BigDecimal.valueOf(100000), TipoTransaccion.DEPOSITO_CAJERO);

        TransaccionStrategy estrategiaMock = mock(TransaccionStrategy.class);
        BigDecimal costoTransaccion = BigDecimal.valueOf(2);
        when(estrategiaMock.getCosto()).thenReturn(costoTransaccion);
        when(strategyFactory.getStrategy(TipoTransaccion.DEPOSITO_CAJERO, TipoOperacion.DEPOSITO)).thenReturn(estrategiaMock);

        TransaccionResponseDTO responseDTO = transaccionService.realizarDeposito(depositoRequestDTO);

        Cuenta cuentaActualizada = cuentaRepository.findById(cuenta.getId()).orElseThrow();

        BigDecimal saldoEsperado = cuenta.getSaldo().add(depositoRequestDTO.getMonto()).subtract(costoTransaccion);
        assertEquals(saldoEsperado.setScale(0, RoundingMode.HALF_UP) , cuentaActualizada.getSaldo().setScale(0, RoundingMode.HALF_UP));
        assertNotNull(responseDTO);
    }


    @Test
    void testRealizarRetiro_Exitoso() {
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(3000), "Juan Perez");
        cuentaRepository.save(cuenta);

        TransaccionRequestDTO retiroRequestDTO = new TransaccionRequestDTO(cuenta.getNumeroCuenta(), BigDecimal.valueOf(1000), TipoTransaccion.RETIRO_CAJERO);

        TransaccionStrategy estrategiaMock = mock(TransaccionStrategy.class);
        BigDecimal costoTransaccion = BigDecimal.valueOf(1);
        when(estrategiaMock.getCosto()).thenReturn(costoTransaccion);

        when(strategyFactory.getStrategy(TipoTransaccion.RETIRO_CAJERO, TipoOperacion.RETIRO)).thenReturn(estrategiaMock);

        TransaccionResponseDTO responseDTO = transaccionService.realizarRetiro(retiroRequestDTO);

        Cuenta cuentaActualizada = cuentaRepository.findById(cuenta.getId()).orElseThrow();

        BigDecimal saldoEsperado = cuenta.getSaldo().subtract(retiroRequestDTO.getMonto()).subtract(costoTransaccion);

        assertEquals(saldoEsperado.setScale(0, RoundingMode.HALF_UP), cuentaActualizada.getSaldo().setScale(0, RoundingMode.HALF_UP));
        assertNotNull(responseDTO);
    }


    @Test
    void testObtenerHistorialPorCuenta() {
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(300), "Juan Perez");
        cuentaRepository.save(cuenta);

        TransaccionRequestDTO depositoRequestDTO = new TransaccionRequestDTO(cuenta.getNumeroCuenta(), BigDecimal.valueOf(1000), TipoTransaccion.DEPOSITO_CAJERO);
        transaccionService.realizarDeposito(depositoRequestDTO);

        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerHistorialPorCuenta(cuenta.getId());

        assertNotNull(transacciones);
        assertFalse(transacciones.isEmpty());
        assertEquals(cuenta.getNumeroCuenta(), transacciones.get(0).getNumeroCuenta());
    }


}

