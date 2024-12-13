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

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TransaccionServiceImpl implements TransaccionService {

    private static final double DEPOSITO_SUCURSAL = 0.0;
    private static final double DEPOSITO_CAJERO= 2.0 ;
    private static final double DEPOSITO_OTRA_CUENTA = 1.50;
    private static final double COMPRA_FISICA = 0.0;
    private static final double COMPRA_EN_LINEA = 5.0;
    private static final double RETIRO_CAJERO = 1.0;

    private final TransaccionRepository transaccionRepository;
    private final CuentaRepository cuentaRepository;

    public TransaccionServiceImpl(TransaccionRepository transaccionRepository, CuentaRepository cuentaRepository) {
        this.transaccionRepository = transaccionRepository;
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<TransaccionResponseDTO> obtenerTransacciones() {
        List<Transaccion> transacciones = transaccionRepository.findAll();
        List<TransaccionResponseDTO> transaccionResponse = transacciones.stream().
                map(t -> new TransaccionResponseDTO(t)).collect(Collectors.toList());
        return transaccionResponse;
    }

    @Override
    public TransaccionResponseDTO realizarDeposito(TransaccionRequestDTO depositoRequestDTO) {
        Cuenta cuenta = cuentaRepository.findById(depositoRequestDTO.getCuentaId()).orElseThrow(
                () -> new NoSuchElementException("Cuenta no encontrada  con el ID: " + depositoRequestDTO.getCuentaId())
        );

        if(depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_CAJERO &&
           depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_OTRA_CUENTA &&
           depositoRequestDTO.getTipoTransaccion() != TipoTransaccion.DEPOSITO_SUCURSAL){
            throw new ConflictException("Tipo de transaccion no valido");
        }

        double monto = depositoRequestDTO.getMonto();
        double costoTransaccion = 0.0;
        if(depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_CAJERO){
            costoTransaccion = DEPOSITO_CAJERO;
        } else if (depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_OTRA_CUENTA) {
            costoTransaccion = DEPOSITO_OTRA_CUENTA;
        } else if (depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_SUCURSAL) {
            costoTransaccion = DEPOSITO_SUCURSAL;
        }
        /**
        if(cuenta.getSaldo() < (monto - costoTransaccion)){
            throw new RuntimeException("Saldo insuficiente, tome en cuenta el costo de la transacción");
        }
*/
        if(cuenta.getSaldo() + (monto - costoTransaccion)< 0){
            throw new ConflictException("Saldo insuficiente, tome en cuenta el costo de la transacción");
        }

        cuenta.setSaldo(cuenta.getSaldo() + monto - costoTransaccion);
        cuentaRepository.save(cuenta);
        /*
        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setMonto(monto);
        transaccion.setTipo(depositoRequestDTO.getTipoTransaccion());
        transaccion.setCostoTransaccion(costoTransaccion);
        transaccion.setFecha(LocalDateTime.now());
*/
        Transaccion transaccion = new Transaccion(monto,
                costoTransaccion,LocalDateTime.now(),
                depositoRequestDTO.getTipoTransaccion(),cuenta);

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion);
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
        double monto = transaccionRequestDTO.getMonto();
        double costoTransaccion = 0.0;

        if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.RETIRO_CAJERO) {
            costoTransaccion = RETIRO_CAJERO;
        } else if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.COMPRA_EN_LINEA) {
            costoTransaccion = COMPRA_EN_LINEA;
        } else if (transaccionRequestDTO.getTipoTransaccion() == TipoTransaccion.COMPRA_FISICA) {
            costoTransaccion = COMPRA_FISICA;
        }

        if(cuenta.getSaldo() < (monto + costoTransaccion)){
            throw new ConflictException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto - costoTransaccion);
        cuentaRepository.save(cuenta);


        Transaccion transaccion = new Transaccion(monto,
                costoTransaccion,LocalDateTime.now(),
                transaccionRequestDTO.getTipoTransaccion(),cuenta);

        /*transaccion.setCuenta(cuenta);
        transaccion.setMonto(monto);
        transaccion.setTipo(transaccionRequestDTO.getTipoTransaccion());
        transaccion.setCostoTransaccion(costoTransaccion);
        transaccion.setFecha(LocalDateTime.now());*/

        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion);
    }

    @Override
    public List<TransaccionResponseDTO> obtenerHistorialPorCuenta(Long cuentaId) {
        List<Transaccion> transacciones = transaccionRepository.findByCuentaId(cuentaId);
        // Validar si la lista de transacciones está vacía
        if (transacciones.isEmpty()) {
            throw new NoSuchElementException("No se encontraron transacciones para la cuenta con ID: " + cuentaId);
        }
        return transacciones.stream().map(transaccion -> new TransaccionResponseDTO(transaccion)).collect(Collectors.toList());
    }
}
