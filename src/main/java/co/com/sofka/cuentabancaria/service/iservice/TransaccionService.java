package co.com.sofka.cuentabancaria.service.iservice;

import co.com.sofka.cuentabancaria.dto.deposito.DepositoRequestDTO;
import co.com.sofka.cuentabancaria.dto.deposito.DepositoResponseDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;

import java.util.List;

public interface TransaccionService {

    List<TransaccionResponseDTO> obtenerTransacciones();
    DepositoResponseDTO realizarDeposito(DepositoRequestDTO depositoRequestDTO);
    TransaccionResponseDTO realizarRetiro(TransaccionRequestDTO transaccionRequestDTO);
    List<TransaccionResponseDTO> obtenerHistorialPorCuenta(Long cuentaId);
}
