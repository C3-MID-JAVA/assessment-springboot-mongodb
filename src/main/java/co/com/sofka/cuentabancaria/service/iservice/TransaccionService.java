package co.com.sofka.cuentabancaria.service.iservice;


import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionRequestDTO;
import co.com.sofka.cuentabancaria.dto.transaccion.TransaccionResponseDTO;

import java.util.List;

public interface TransaccionService {

    List<TransaccionResponseDTO> obtenerTransacciones();
    TransaccionResponseDTO realizarDeposito(TransaccionRequestDTO transaccionRequestDTO);
    TransaccionResponseDTO realizarRetiro(TransaccionRequestDTO transaccionRequestDTO);
    List<TransaccionResponseDTO> obtenerHistorialPorCuenta(Long cuentaId);
}
