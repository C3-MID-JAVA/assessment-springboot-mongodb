package org.bankAccountManager.service.interfaces;

import org.bankAccountManager.entity.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(Account account);

    Account getAccountById(int id);

    Account getAccountByCustomerId(int customer_id);

    List<Account> getAllAccounts();

    Account updateAccount(Account account);

    void deleteAccount(Account account);
}
