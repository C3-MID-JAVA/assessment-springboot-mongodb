package org.bankAccountManager.service.implementations;

import org.bankAccountManager.entity.Account;
import org.bankAccountManager.repository.AccountRepository;
import org.bankAccountManager.repository.CustomerRepository;
import org.bankAccountManager.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    public AccountServiceImplementation(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Account createAccount(Account account) {
        if (accountRepository.existsById(account.getId()))
            throw new IllegalArgumentException("Account already exists");
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(int id) {
        return accountRepository.findAccountById(id);
    }

    @Override
    public List<Account> getAccountsByCustomerId(int customer_id) {
        return customerRepository.findCustomerById(customer_id).getAccounts();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }
}
