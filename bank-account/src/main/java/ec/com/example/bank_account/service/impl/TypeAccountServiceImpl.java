package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.TypeAccountRequestDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.TypeAccountMapper;
import ec.com.example.bank_account.repository.TypeAccountRepository;
import ec.com.example.bank_account.service.TypeAccountService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeAccountServiceImpl implements TypeAccountService {

    private final TypeAccountRepository typeAccountRepository;
    private final TypeAccountMapper typeAccountMapper;

    public TypeAccountServiceImpl(TypeAccountRepository typeAccountRepository, TypeAccountMapper typeAccountMapper) {
        this.typeAccountRepository = typeAccountRepository;
        this.typeAccountMapper = typeAccountMapper;
    }

    @Override
    public TypeAccountResponseDTO createTypeAccount(TypeAccountRequestDTO typeAccount) {
        return typeAccountMapper.mapToDTO(typeAccountRepository.save(typeAccountMapper.mapToEntity(typeAccount)));
    }

    @Override
    public List<TypeAccountResponseDTO> getAllTypeAccount() {
        List<TypeAccountResponseDTO> response = typeAccountRepository.findAll()
               .stream().map(typeAccountMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No typesAccount records found.");
        }
        return response;
    }
}