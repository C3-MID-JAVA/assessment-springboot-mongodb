package es.cuenta_bancaria_BD.service;


import es.cuenta_bancaria_BD.dto.TransactionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ITransactionService {
    List<TransactionDTO> listarTransacciones();
    TransactionDTO obtenerTransaccionPorId(String id);
}
