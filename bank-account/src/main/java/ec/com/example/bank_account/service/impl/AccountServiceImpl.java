package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.AccountRequestDTO;
import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.AccountMapper;
import ec.com.example.bank_account.repository.AccountRepository;
import ec.com.example.bank_account.service.AccountService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        return accountMapper.mapToDTO(accountRepository.save(accountMapper.mapToEntity(accountRequestDTO)));
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        List<AccountResponseDTO> response = accountRepository.findAll().stream()
                .map(accountMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No accounts records found.");
        }
        return response;
    }
}
