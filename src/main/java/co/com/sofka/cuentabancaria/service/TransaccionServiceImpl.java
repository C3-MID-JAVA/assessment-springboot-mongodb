package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.config.exceptions.ConflictException;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.model.Transaccion;
import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.repository.TransaccionRepository;
import co.com.sofka.cuentabancaria.service.iservice.TransaccionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private static final BigDecimal DEPOSITO_SUCURSAL = BigDecimal.ZERO;
    private static final BigDecimal DEPOSITO_CAJERO= BigDecimal.valueOf(2.0);
    private static final BigDecimal DEPOSITO_OTRA_CUENTA = BigDecimal.valueOf(1.50);
    private static final BigDecimal COMPRA_FISICA = BigDecimal.ZERO;
    private static final BigDecimal COMPRA_EN_LINEA = BigDecimal.valueOf(5.0);
    private static final BigDecimal RETIRO_CAJERO = BigDecimal.valueOf(1.0);

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }
/*
    @Override
    public List<TransaccionResponseDTO> obtenerTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        List<TransaccionResponseDTO> transaccionResponse = transacciones.stream().
                map(t -> new TransaccionResponseDTO(t)).collect(Collectors.toList());
        return transaccionResponse;
    }

    */
    @Override
    public List<TransaccionResponseDTO> obtenerTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();

        return transacciones.stream()
                .map(this::mapearTransaccionAResponseDTO)
                .collect(Collectors.toList());
    }



    @Override
    public TransaccionResponseDTO realizarDeposito(TransaccionRequestDTO depositoRequestDTO) {
        if (depositoRequestDTO == null) {
            throw new IllegalArgumentException("El cuerpo de la solicitud no puede ser nulo");
        }

        Cuenta cuenta = cuentaRepository.findById(depositoRequestDTO.getCuentaId()).orElseThrow(
                () -> new NoSuchElementException("Cuenta no encontrada  con el ID: " + depositoRequestDTO.getCuentaId())
        );

        if(depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_CAJERO &&
           depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_OTRA_CUENTA &&
           depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_SUCURSAL){
            throw new ConflictException("Tipo de transaccion no valido");
        }

        BigDecimal monto = depositoRequestDTO.getMonto();
        BigDecimal costoTransaccion = BigDecimal.ZERO;

        if(depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_CAJERO){
            costoTransaccion = DEPOSITO_CAJERO;
        } else if (depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_OTRA_CUENTA) {
            costoTransaccion = DEPOSITO_OTRA_CUENTA;
        } else if (depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_SUCURSAL) {
            costoTransaccion = DEPOSITO_SUCURSAL;
        }

        BigDecimal saldoCuenta = cuenta.getSaldo();
        BigDecimal saldoFinal = saldoCuenta.add(monto).subtract(costoTransaccion);

        if(saldoFinal.compareTo(BigDecimal.ZERO)< 0){
            throw new ConflictException("Saldo insuficiente, tome en cuenta el costo de la transacción");
        }

        cuenta.setSaldo(saldoFinal);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion(monto,
                costoTransaccion,LocalDateTime.now(),
                depositoRequestDTO.getTipoTransaccion(),cuenta.getId());

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion,cuenta.getSaldo());
    }

    @Override
    public TransaccionResponseDTO realizarRetiro(TransaccionRequestDTO transaccionRequestDTO) {

        Cuenta cuenta = cuentaRepository.findById(transaccionRequestDTO.getCuentaId()).orElseThrow(
                () -> new NoSuchElementException("Cuenta no encontrada  con el ID: " + transaccionRequestDTO.getCuentaId())
        );

        if(transaccionRequestDTO.getTipoTransaccion() != TipoTransaccion.RETIRO_CAJERO &&
           transaccionRequestDTO.getTipoTransaccion() != TipoTransaccion.COMPRA_EN_LINEA &&
           transaccionRequestDTO.getTipoTransaccion() != TipoTransaccion.COMPRA_FISICA){
            throw new ConflictException("Tipo de transaccion no valido para retiro o compra");
        }
        BigDecimal monto = transaccionRequestDTO.getMonto();
        BigDecimal costoTransaccion = BigDecimal.ZERO;

        if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.RETIRO_CAJERO) {
            costoTransaccion = RETIRO_CAJERO;
        } else if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.COMPRA_EN_LINEA) {
            costoTransaccion = COMPRA_EN_LINEA;
        } else if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.COMPRA_FISICA) {
            costoTransaccion = COMPRA_FISICA;
        }

        BigDecimal saldoCuenta = cuenta.getSaldo();
        BigDecimal montoConCosto  = monto.add(costoTransaccion);

        if(saldoCuenta.compareTo(montoConCosto) <0){
            throw new ConflictException("Saldo insuficiente");
        }

        cuenta.setSaldo(saldoCuenta.subtract(montoConCosto));
        cuentaRepository.save(cuenta);


        Transaccion transaccion = new Transaccion(monto,
                costoTransaccion,LocalDateTime.now(),
                transaccionRequestDTO.getTipoTransaccion(),cuenta.getId());

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion,cuenta.getSaldo());
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
