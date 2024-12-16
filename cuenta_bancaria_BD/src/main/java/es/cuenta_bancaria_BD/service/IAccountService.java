package es.cuenta_bancaria_BD.service;


import es.cuenta_bancaria_BD.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface IAccountService {
    List<AccountDTO> listarCuentas();
    AccountDTO crearCuenta(AccountDTO accountDTO);
    AccountDTO obtenerCuentaPorId(String id);
    AccountDTO realizarTransaccion(String cuentaId, BigDecimal monto, String tipo);
}
