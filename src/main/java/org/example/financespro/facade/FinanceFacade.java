package org.example.financespro.facade;

import org.example.financespro.dto.request.AccountRequestDto;
import org.example.financespro.dto.request.TransactionRequestDto;
import org.example.financespro.dto.response.AccountResponseDto;
import org.example.financespro.dto.response.TransactionResponseDto;
import org.example.financespro.service.AccountService;
import org.example.financespro.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class FinanceFacade {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public FinanceFacade(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    public AccountResponseDto createAccount(AccountRequestDto request) {
        return accountService.createAccount(request);
    }

    public AccountResponseDto getAccountDetails(AccountRequestDto request) {
        return accountService.getAccountDetailsByNumber(request.getNumber());
    }

    public TransactionResponseDto processTransaction(TransactionRequestDto request) {
        return transactionService.processTransaction(request);
    }
}
