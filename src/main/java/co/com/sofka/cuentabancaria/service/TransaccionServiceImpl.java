package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.config.exceptions.ConflictException;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.model.Transaccion;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.repository.TransaccionRepository;
import co.com.sofka.cuentabancaria.service.iservice.TransaccionService;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategy;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategyContext;
import co.com.sofka.cuentabancaria.service.strategy.TransaccionStrategyFactory;
import co.com.sofka.cuentabancaria.service.strategy.enums.TipoOperacion;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;
    private final TransaccionStrategyFactory strategyFactory;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository, TransaccionStrategyFactory strategyFactory) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
        this.strategyFactory = strategyFactory;
    }

    @Override
    public List<TransaccionResponseDTO> obtenerTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();

        return transacciones.stream()
                .map(this::mapearTransaccionAResponseDTO)
                .collect(Collectors.toList());
    }


    private TransaccionStrategyContext obtenerCuentaYStrategy(TransaccionRequestDTO requestDTO, TipoOperacion tipo) {
        Cuenta cuenta = cuentaRepository.findById(requestDTO.getCuentaId()).orElseThrow(
                () -> new NoSuchElementException("Cuenta no encontrada con el ID: " + requestDTO.getCuentaId())
        );

        TransaccionStrategy strategy = strategyFactory.getStrategy(requestDTO.getTipoTransaccion(),tipo);

        BigDecimal monto = requestDTO.getMonto();
        strategy.validar(cuenta, monto);

        return new TransaccionStrategyContext(cuenta, strategy, monto);
    }


    @Override
    public TransaccionResponseDTO realizarDeposito(TransaccionRequestDTO depositoRequestDTO) {
        TransaccionStrategyContext context = obtenerCuentaYStrategy(depositoRequestDTO, TipoOperacion.DEPOSITO);
        Cuenta cuenta = context.getCuenta();
        TransaccionStrategy strategy = context.getStrategy();
        BigDecimal monto = context.getMonto();
        BigDecimal costoTransaccion = strategy.getCosto();
        BigDecimal saldoCuenta = cuenta.getSaldo();
        BigDecimal saldoFinal = saldoCuenta.add(monto).subtract(costoTransaccion);

        if (saldoFinal.compareTo(BigDecimal.ZERO) < 0) {
            throw new ConflictException("Saldo insuficiente, tome en cuenta el costo de la transacción");
        }

        cuenta.setSaldo(saldoFinal);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion(monto, costoTransaccion, LocalDateTime.now(),
                depositoRequestDTO.getTipoTransaccion(), cuenta.getId());

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion, cuenta.getSaldo());
    }



    @Override
    public TransaccionResponseDTO realizarRetiro(TransaccionRequestDTO transaccionRequestDTO) {
        TransaccionStrategyContext context = obtenerCuentaYStrategy(transaccionRequestDTO, TipoOperacion.RETIRO);
        Cuenta cuenta = context.getCuenta();
        TransaccionStrategy strategy = context.getStrategy();
        BigDecimal monto = context.getMonto();
        BigDecimal costoTransaccion = strategy.getCosto();
        BigDecimal montoConCosto = monto.add(costoTransaccion);

        if (cuenta.getSaldo().compareTo(montoConCosto) < 0) {
            throw new ConflictException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo().subtract(montoConCosto));
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion(monto, costoTransaccion, LocalDateTime.now(),
                transaccionRequestDTO.getTipoTransaccion(), cuenta.getId());

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion, cuenta.getSaldo());
    }


    @Override
    public List<TransaccionResponseDTO> obtenerHistorialPorCuenta(String cuentaId) {
        List<Transaccion> transacciones = transaccionRepository.findByCuentaId(cuentaId);

        return transacciones.stream()
                .map(this::mapearTransaccionAResponseDTO)
                .collect(Collectors.toList());
    }


    private TransaccionResponseDTO mapearTransaccionAResponseDTO(Transaccion transaccion) {
        Cuenta cuenta = cuentaRepository.findById(transaccion.getCuentaId()).orElseThrow(
                () -> new NoSuchElementException("Cuenta no encontrada con el ID: " + transaccion.getCuentaId())
        );

        return new TransaccionResponseDTO(transaccion, cuenta.getSaldo());
    }

}
