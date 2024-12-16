package com.example.bank.service;

import com.example.bank.dto.OperationDTO;
import com.example.bank.entity.Operation;

import java.util.List;

public interface OperationService {
    List<OperationDTO> getAllOperations();
    OperationDTO getOperationById(String id);
    OperationDTO createOperation(OperationDTO operationDTO);
    private Operation applyOperation(Operation operation) { return operation; };
    void revertOperation(int id);
}


