package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.dto.deposito.DepositoRequestDTO;
import co.com.sofka.cuentabancaria.dto.deposito.DepositoResponseDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.model.Transaccion;
import co.com.sofka.cuentabancaria.model.enums.TipoTransaccion;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.repository.TransaccionRepository;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import co.com.sofka.cuentabancaria.service.iservice.TransaccionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public DepositoResponseDTO realizarDeposito(DepositoRequestDTO depositoRequestDTO) {
        Cuenta cuenta = cuentaRepository.findById(depositoRequestDTO.getCuentaId()).orElseThrow(
                () -> new RuntimeException("Cuenta no encontrada  con el ID: " + depositoRequestDTO.getCuentaId())
        );

        double monto = depositoRequestDTO.getMonto();
        double costoTransaccion = 0.0;
        if(depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_CAJERO){
            costoTransaccion = DEPOSITO_CAJERO;
        } else if (depositoRequestDTO.getTipoTransaccion() == TipoTransaccion.DEPOSITO_OTRA_CUENTA) {
            costoTransaccion = DEPOSITO_OTRA_CUENTA;
        }

        cuenta.setSaldo(cuenta.getSaldo() + monto - costoTransaccion);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setMonto(monto);
        transaccion.setTipo(depositoRequestDTO.getTipoTransaccion());
        transaccion.setCostoTransaccion(costoTransaccion);
        transaccion.setFecha(LocalDateTime.now());
        transaccionRepository.save(transaccion);

        return new DepositoResponseDTO(transaccion);
    }

    @Override
    public TransaccionResponseDTO realizarRetiro(TransaccionRequestDTO transaccionRequestDTO) {

        Cuenta cuenta = cuentaRepository.findById(transaccionRequestDTO.getCuentaId()).orElseThrow(
                () -> new RuntimeException("Cuenta no encontrada  con el ID: " + transaccionRequestDTO.getCuentaId())
        );


        double monto = transaccionRequestDTO.getMonto();
        double costoTransaccion = RETIRO_CAJERO;

        if(cuenta.getSaldo() < (monto + costoTransaccion)){
            throw new RuntimeException("Saldo insuficiente");
        }

        cuenta.setSaldo(cuenta.getSaldo() - monto - costoTransaccion);
        cuentaRepository.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setCuenta(cuenta);
        transaccion.setMonto(-monto);
        transaccion.setTipo(transaccionRequestDTO.getTipoTransaccion());
        transaccion.setCostoTransaccion(costoTransaccion);
        transaccion.setFecha(LocalDateTime.now());
        transaccionRepository.save(transaccion);

        return new TransaccionResponseDTO(transaccion);
    }

    @Override
    public List<TransaccionResponseDTO> obtenerHistorialPorCuenta(Long cuentaId) {
        List<Transaccion> transacciones = transaccionRepository.findByCuentaId(cuentaId);

        return transacciones.stream().map(transaccion -> new TransaccionResponseDTO(transaccion)).collect(Collectors.toList());
    }
}





















