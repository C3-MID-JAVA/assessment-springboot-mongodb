package com.example.bank.mapper;

import com.example.bank.dto.AccountDTO;
import com.example.bank.entity.Account;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AccountDTOMapper {
    public static AccountDTO toAccountDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getHolder(),
                account.getBalance(),
                account.getOperations().stream().map(OperationDTOMapper::toOperationDTO).toList()
        );
    }

    public static Account toAccount(AccountDTO accountDTO) {
        return new Account(
                accountDTO.getId(),
                accountDTO.getHolder(),
                accountDTO.getBalance(),
                accountDTO.getOperations().stream().map(OperationDTOMapper::toOperation).toList()
        );
    }
}
