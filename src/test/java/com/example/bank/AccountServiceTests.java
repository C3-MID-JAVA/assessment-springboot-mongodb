package com.example.bank;

import com.example.bank.dto.AccountDTO;
import com.example.bank.repository.AccountRepository;
import com.example.bank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void accountCreatedOK () {
        AccountDTO newAccount = new AccountDTO("nat", BigDecimal.valueOf(123456));
        String accountId = newAccount.getId();
        AccountDTO response = accountService.createAccount(newAccount);
        assertNotNull(response);
        verify(accountService.getAccountById(accountId));
    }
}
