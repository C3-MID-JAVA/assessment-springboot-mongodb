package com.example.bank.service;

import com.example.bank.dto.AccountDTO;
import com.example.bank.dto.OperationDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(String id);
    AccountDTO createAccount(AccountDTO accountDTO);
    void deleteAccount(int id);
}
