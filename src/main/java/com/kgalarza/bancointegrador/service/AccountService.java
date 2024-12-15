package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.AccountInDto;
import com.kgalarza.bancointegrador.model.dto.AccountOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    AccountOutDto createAccount(AccountInDto cuentaInDto);

    List<AccountOutDto> getAllAccounts();

    AccountOutDto getAccountById(String id);

    AccountOutDto updateAccount(String id, AccountInDto cuentaInDto);

    void deleteAccount(String id);
}
