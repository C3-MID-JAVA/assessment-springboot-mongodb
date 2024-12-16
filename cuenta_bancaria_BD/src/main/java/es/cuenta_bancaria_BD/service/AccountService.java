package es.cuenta_bancaria_BD.service;


import es.cuenta_bancaria_BD.dto.AccountDTO;
import es.cuenta_bancaria_BD.enums.TypeTransaction;
import es.cuenta_bancaria_BD.exception.CuentaNoEncontradaException;
import es.cuenta_bancaria_BD.exception.SaldoInsuficienteException;
import es.cuenta_bancaria_BD.mapper.AccountMapper;
import es.cuenta_bancaria_BD.model.Account;
import es.cuenta_bancaria_BD.model.Transaction;
import es.cuenta_bancaria_BD.repository.AccountRepository;
import es.cuenta_bancaria_BD.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private final AccountMapper dtoMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper dtoMapper,TransactionRepository transactionRepository) {
        this.dtoMapper = dtoMapper;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<AccountDTO> listarCuentas() {
        var response = accountRepository.findAll();
        return response
                .stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO crearCuenta(AccountDTO accountDTO) {
        Account account = dtoMapper.toEntity(accountDTO);
        account.setTransacciones(new ArrayList<>());
        Account response = accountRepository.save(account);
        return dtoMapper.toDto(response);
    }

    @Override
    public AccountDTO obtenerCuentaPorId(String id) {
        var response = accountRepository.findById(id).orElse(null);
        if(response != null){
            return dtoMapper.toDto(response);
        }
        return null;
    }

    @Override
    public AccountDTO realizarTransaccion(String cuentaId, BigDecimal monto, String tipo) {
        var cuenta  = accountRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaNoEncontradaException("La cuenta con ID " + cuentaId + " no fue encontrada"));

        TypeTransaction tipoTransaccion = TypeTransaction.fromString(tipo);
        if (tipoTransaccion == null) {
            throw new IllegalArgumentException("Tipo de transacción no válido: " + tipo);
        }

        BigDecimal costo = tipoTransaccion.getCosto();

        if (tipo.startsWith("RETIRO") || tipo.equals("COMPRA_WEB") || tipo.equals("COMPRA_ESTABLECIMIENTO")) {
            BigDecimal totalADescontar = monto.add(costo);

            if (cuenta.getSaldo().compareTo(totalADescontar) < 0) {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar esta transacción");
            }

            cuenta.setSaldo(cuenta.getSaldo().subtract(totalADescontar));
        } else {
            cuenta.setSaldo(cuenta.getSaldo().add(monto).subtract(costo)); // Descontar costo del depósito
        }
        Transaction transaccion = new Transaction();
        transaccion.setMonto(monto);
        transaccion.setTipo(tipo);
        transaccion.setCosto(costo);
        transaccion.setIdCuenta(cuentaId);

        //cuenta.setSaldo(cuenta.getSaldo().add(montoFinal));
        accountRepository.save(cuenta);
        transactionRepository.save(transaccion);
        return dtoMapper.toDto(cuenta);
    }

}
