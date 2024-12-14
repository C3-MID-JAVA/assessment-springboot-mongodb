package com.bankmanagement.bankmanagement.service.impl;

import com.bankmanagement.bankmanagement.dto.AccountRequestDTO;
import com.bankmanagement.bankmanagement.dto.AccountResponseDTO;
import com.bankmanagement.bankmanagement.mapper.AccountMapper;
import com.bankmanagement.bankmanagement.model.Account;
import com.bankmanagement.bankmanagement.model.User;
import com.bankmanagement.bankmanagement.repository.AccountRepository;
import com.bankmanagement.bankmanagement.repository.UserRespository;
import com.bankmanagement.bankmanagement.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRespository userRespository;

    public AccountServiceImpl(UserRespository userRespository, AccountRepository accountRepository) {
        this.userRespository = userRespository;
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountResponseDTO create(AccountRequestDTO accountRequestDTO) {
        User user = userRespository.findById(accountRequestDTO.getUserId()).orElseThrow();
        Account account = new Account();
        account.setUser(user);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.fromEntity(savedAccount);
    }

    @Override
    public List<AccountResponseDTO> getAllByUserId(UUID userId){
        User user = userRespository.findById(userId).orElseThrow();
        return accountRepository.findAllByUser(user).stream().map(AccountMapper::fromEntity).toList();
    }

    @Override
    public AccountResponseDTO findByAccountNumber(String accountNumber) {
        return AccountMapper.fromEntity(accountRepository.findByAccountNumber(accountNumber));
    }
}
