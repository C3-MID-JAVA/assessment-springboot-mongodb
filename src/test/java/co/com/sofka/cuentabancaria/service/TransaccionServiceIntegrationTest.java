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

    @Autowired
    private TransaccionServiceImpl transaccionService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;
    @Autowired
    private TransaccionStrategyFactory transaccionStrategyFactory;


    @Mock
    private TransaccionStrategyFactory strategyFactory;

    @BeforeEach
    void setUp() {
        cuentaRepository.deleteAll();
        transaccionRepository.deleteAll();
    }

    @Test
    void testRealizarDeposito_Exitoso() {
        // Crear y guardar una cuenta en la base de datos
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(100000), "Juan Perez");
        cuentaRepository.save(cuenta);

        // Crear un DTO de depósito
        TransaccionRequestDTO depositoRequestDTO = new TransaccionRequestDTO(cuenta.getId(), BigDecimal.valueOf(100000), TipoTransaccion.DEPOSITO_CAJERO);

        // Crear un mock de la estrategia de transacción
        TransaccionStrategy estrategiaMock = mock(TransaccionStrategy.class);
        BigDecimal costoTransaccion = BigDecimal.valueOf(2);  // Suponiendo un costo de 2 para la transacción
        when(estrategiaMock.getCosto()).thenReturn(costoTransaccion);  // Establecer el costo para la transacción

        // Configurar el mock de strategyFactory para devolver la estrategia mockeada
        when(strategyFactory.getStrategy(TipoTransaccion.DEPOSITO_CAJERO, TipoOperacion.DEPOSITO)).thenReturn(estrategiaMock);

        // Ejecutar el depósito
        TransaccionResponseDTO responseDTO = transaccionService.realizarDeposito(depositoRequestDTO);

        // Consultar la cuenta después del depósito
        Cuenta cuentaActualizada = cuentaRepository.findById(cuenta.getId()).orElseThrow();

        // Verificar que el saldo haya aumentado correctamente, considerando el costo de la transacción
        BigDecimal saldoEsperado = cuenta.getSaldo().add(depositoRequestDTO.getMonto()).subtract(costoTransaccion);  // Calcula el saldo esperado
        assertEquals(BigDecimal.valueOf(199998).setScale(0, RoundingMode.HALF_UP), cuentaActualizada.getSaldo().setScale(0, RoundingMode.HALF_UP));
        assertNotNull(responseDTO);  // Verificar que el responseDTO no sea nulo
    }


    @Test
    void testRealizarRetiro_Exitoso() {
        // Crear y guardar una cuenta en la base de datos
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(3000), "Juan Perez");
        cuentaRepository.save(cuenta);

        // Crear un DTO de retiro
        TransaccionRequestDTO retiroRequestDTO = new TransaccionRequestDTO(cuenta.getId(), BigDecimal.valueOf(1000), TipoTransaccion.RETIRO_CAJERO);

        // Crear un mock de la estrategia de transacción
        TransaccionStrategy estrategiaMock = mock(TransaccionStrategy.class);
        BigDecimal costoTransaccion = BigDecimal.valueOf(1);  // Suponiendo un costo de 2 para la transacción
        when(estrategiaMock.getCosto()).thenReturn(costoTransaccion);  // Establecer el costo para la transacción

        // Configurar el mock de strategyFactory para devolver la estrategia mockeada
        when(strategyFactory.getStrategy(TipoTransaccion.RETIRO_CAJERO, TipoOperacion.RETIRO)).thenReturn(estrategiaMock);

        // Ejecutar el retiro
        TransaccionResponseDTO responseDTO = transaccionService.realizarRetiro(retiroRequestDTO);

        // Consultar la cuenta después del retiro
        Cuenta cuentaActualizada = cuentaRepository.findById(cuenta.getId()).orElseThrow();

        // Calcular el saldo esperado, considerando el monto a retirar y el costo de la transacción
        BigDecimal saldoEsperado = cuenta.getSaldo().subtract(retiroRequestDTO.getMonto()).subtract(costoTransaccion);

        // Verificar que el saldo se haya actualizado correctamente
        assertEquals(saldoEsperado.setScale(0, RoundingMode.HALF_UP), cuentaActualizada.getSaldo().setScale(0, RoundingMode.HALF_UP));
        assertNotNull(responseDTO);  // Verificar que el responseDTO no sea nulo
    }


    @Test
    void testObtenerHistorialPorCuenta() {
        // Crear y guardar una cuenta en la base de datos
        Cuenta cuenta = new Cuenta("9876543210", BigDecimal.valueOf(300), "Juan Perez");
        cuentaRepository.save(cuenta);

        // Crear un DTO de transacción
        TransaccionRequestDTO depositoRequestDTO = new TransaccionRequestDTO(cuenta.getId(), BigDecimal.valueOf(1000), TipoTransaccion.DEPOSITO_CAJERO);
        transaccionService.realizarDeposito(depositoRequestDTO);

        // Consultar el historial de transacciones
        List<TransaccionResponseDTO> transacciones = transaccionService.obtenerHistorialPorCuenta(cuenta.getId());

        // Verificar que la transacción se haya guardado correctamente
        assertNotNull(transacciones);
        assertFalse(transacciones.isEmpty());
        assertEquals(cuenta.getId(), transacciones.get(0).getCuentaId());
    }


}

