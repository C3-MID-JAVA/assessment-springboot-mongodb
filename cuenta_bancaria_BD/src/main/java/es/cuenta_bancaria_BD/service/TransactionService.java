package es.cuenta_bancaria_BD.service;


import es.cuenta_bancaria_BD.dto.TransactionDTO;
import es.cuenta_bancaria_BD.mapper.TransactionMapper;
import es.cuenta_bancaria_BD.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService{

    private final TransactionRepository transactionRepository;
    private final TransactionMapper dtoMapper;


    public TransactionService(TransactionRepository transactionRepository, TransactionMapper dtoMapper){
        this.dtoMapper = dtoMapper;
        this.transactionRepository = transactionRepository;

    }
    @Override
    public List<TransactionDTO> listarTransacciones() {
       /* var response = transactionRepository.findAll();
        return response
                .stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());*/

        var response = transactionRepository.findAll();
        if (response == null) {
            return List.of(); // Si la respuesta es null, retorna una lista vacía.
        }
        return response.stream()
                .map(dtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDTO obtenerTransaccionPorId(String id) {
        var response = transactionRepository.findById(id).orElse(null);
        if(response != null){
            return  dtoMapper.toDto(response);
        }

        return null;
    }
}
