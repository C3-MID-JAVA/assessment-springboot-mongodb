package com.sofka.bank.service.impl;

import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.entity.BankAccount;
import com.sofka.bank.mapper.DTOMapper;
import com.sofka.bank.repository.BankAccountRepository;
import com.sofka.bank.service.BankAccountService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository){
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccountDTO createAccount(BankAccountDTO bankAccountDTO) {

        if (bankAccountDTO.getAccountHolder() == null || bankAccountDTO.getAccountHolder().isEmpty()) {
            throw new IllegalArgumentException("Account holder is required");
        }
        if (bankAccountDTO.getGlobalBalance() == null || bankAccountDTO.getGlobalBalance() < 0) {
            throw new IllegalArgumentException("Global balance must be a positive number");
        }


        if (!isAccountNumberUnique(bankAccountDTO.getAccountNumber())) {
            throw new IllegalArgumentException("Account number already exists");
        }


        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolder(bankAccountDTO.getAccountHolder());
        bankAccount.setGlobalBalance(bankAccountDTO.getGlobalBalance());


        String accountNumber = bankAccountDTO.getAccountNumber() != null ? bankAccountDTO.getAccountNumber()
                :"1000" + System.currentTimeMillis();
        bankAccount.setAccountNumber(accountNumber);


        return DTOMapper.toBankAccountDTO(bankAccountRepository.save(bankAccount));
    }

    @Override
    public List<BankAccountDTO> getAllAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        if (bankAccounts.isEmpty()) {
            return Collections.emptyList();
        }
        return bankAccounts.stream()
                .map(DTOMapper::toBankAccountDTO)
                .collect(Collectors.toList());

    }

    public boolean isAccountNumberUnique(String accountNumber) {
        return !bankAccountRepository.existsByAccountNumber(accountNumber);
    }
}