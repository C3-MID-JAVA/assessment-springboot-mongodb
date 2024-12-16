package com.example.bank.mapper;

import com.example.bank.dto.OperationDTO;
import com.example.bank.entity.Account;
import com.example.bank.entity.Operation;

public class OperationDTOMapper {
    public static OperationDTO toOperationDTO(Operation operation) {
        return new OperationDTO(
                operation.getValue(),
                operation.getType(),
                operation.getAccount().getId()
        );
    }

    public static Operation toOperation(OperationDTO operationDTO) {
        return new Operation(
                operationDTO.getId(),
                operationDTO.getValue(),
                operationDTO.getType(),
                new Account(operationDTO.getAccountId())
        );
    }

    public static Operation toOperation(OperationDTO operationDTO, Account account) {
        return new Operation(
                operationDTO.getId(),
                operationDTO.getValue(),
                operationDTO.getType(),
                account
        );
    }
}
