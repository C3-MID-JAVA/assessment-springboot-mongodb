package edisonrmedina.CityBank.service.impl;

import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.repository.BankAccountRepository;
import edisonrmedina.CityBank.service.BankAccountService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ComponentScan
public class BankAccountServiceImp implements BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountServiceImp(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public Optional<BankAccount> getBankAccount(String id) {
        return bankAccountRepository.findById(id);
    }

    public void updateBankAccount(Optional<BankAccount> account) {

        if (account == null || account.get().getId() == null) {
            throw new IllegalArgumentException("La cuenta bancaria no puede ser nula o carecer de ID");
        }

        bankAccountRepository.save(account.get()); // Actualiza el registro en la base de datos
    }

    public BankAccount register(BankAccount bankAccount) {
        if (bankAccount == null) {
            throw new IllegalArgumentException("Bank account cannot be null");
        }
        if (bankAccount.getAccountHolder() == null || bankAccount.getAccountHolder().isEmpty()) {
            throw new IllegalArgumentException("Account holder cannot be null or empty");
        }

        bankAccountRepository.save(bankAccount);
        return bankAccount;
    }

}
