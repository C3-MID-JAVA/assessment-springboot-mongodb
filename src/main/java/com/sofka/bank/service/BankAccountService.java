package com.sofka.bank.service;

import com.sofka.bank.dto.BankAccountDTO;

import java.util.List;

public interface BankAccountService {
    BankAccountDTO createAccount(BankAccountDTO bankAccountDTO);
    List<BankAccountDTO> getAllAccounts ();
}
