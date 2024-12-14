package co.com.sofka.cuentabancaria.service.iservice;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {
    List<CuentaResponseDTO> obtenerCuentas();
    CuentaResponseDTO crearCuenta(CuentaRequestDTO cuentaRequestDTO);
    CuentaResponseDTO obtenerCuentaPorId(String id);
    BigDecimal consultarSaldo(String id);

}
