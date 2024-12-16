package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.TypeTransactionRequestDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.TypeTransactionMapper;
import ec.com.example.bank_account.repository.TypeTransactionRepository;
import ec.com.example.bank_account.service.TypeTransactionService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeTransactionServiceImpl implements TypeTransactionService {
    private final TypeTransactionRepository typeTransactionRepository;
    private final TypeTransactionMapper typeTransactionMapper;

    public TypeTransactionServiceImpl(TypeTransactionRepository typeTransactionRepository, TypeTransactionMapper typeTransactionMapper) {
        this.typeTransactionRepository = typeTransactionRepository;
        this.typeTransactionMapper = typeTransactionMapper;
    }

    @Override
    public TypeTransactionResponseDTO createTypeTransaction(TypeTransactionRequestDTO typeTransaction) {
        return typeTransactionMapper.mapToDTO(typeTransactionRepository.save(typeTransactionMapper.mapToEntity(typeTransaction)));
    }

    @Override
    public List<TypeTransactionResponseDTO> getAllTypeTransactions() {
        List<TypeTransactionResponseDTO> response = typeTransactionRepository.findAll()
                .stream().map(typeTransactionMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No typesTransaction records found.");
        }
        return response;
    }
}
