package com.example.bank;

import com.example.bank.dto.OperationDTO;
import com.example.bank.repository.OperationRepository;
import com.example.bank.service.impl.OperationServiceImpl;
import com.example.bank.utils.operations.OperationTypesEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTests {
    @Mock
    OperationRepository operationRepository;

    @InjectMocks
    OperationServiceImpl operationService;

    @Test
    void operationCreatedOK() {
        String accountId = "36f53041-"; // olivia's account
        OperationDTO newOperation = new OperationDTO(BigDecimal.valueOf(100), OperationTypesEnum.ACCOUNT_DEPOSIT, accountId);
        String operationId = newOperation.getId();

        OperationDTO response = operationService.createOperation(newOperation);
        assertNotNull(response);
        verify(operationService.getOperationById(operationId));
    }
}
