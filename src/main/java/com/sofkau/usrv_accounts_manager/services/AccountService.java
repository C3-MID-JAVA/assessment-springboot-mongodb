package com.sofkau.usrv_accounts_manager.services;

import com.sofkau.usrv_accounts_manager.dto.AccountDTO;

import java.util.ArrayList;
import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO account);
    List<AccountDTO> getAllAccounts();
}
