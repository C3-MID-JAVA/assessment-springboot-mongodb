package com.sofka.bank.service;

import com.sofka.bank.dto.BankAccountDTO;
import com.sofka.bank.entity.BankAccount;
import com.sofka.bank.repository.BankAccountRepository;
import com.sofka.bank.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccountDTO validBankAccountDTO;

    @BeforeEach
    public void setup() {
        validBankAccountDTO = new BankAccountDTO(null, "1000008","John Doe", 1000.0,new ArrayList<>());
    }

    @Test
    @DisplayName("Should successfully create account with provided data")
    public void testCreateAccount_Success() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountHolder(validBankAccountDTO.getAccountHolder());
        bankAccount.setGlobalBalance(validBankAccountDTO.getGlobalBalance());
        bankAccount.setAccountNumber(validBankAccountDTO.getAccountNumber());
        bankAccount.setId("randomGeneratedId");
        bankAccount.setTransactions(new ArrayList<>());

        Mockito.when(bankAccountRepository.existsByAccountNumber(validBankAccountDTO.getAccountNumber())).thenReturn(false);
        Mockito.when(bankAccountRepository.save(Mockito.any(BankAccount.class))).thenReturn(bankAccount);

        BankAccountDTO createdAccountDTO = bankAccountService.createAccount(validBankAccountDTO);

        assertNotNull(createdAccountDTO);
        assertEquals("John Doe", createdAccountDTO.getAccountHolder());
        assertEquals(1000.0, createdAccountDTO.getGlobalBalance());
        assertEquals("1000008", createdAccountDTO.getAccountNumber());
        assertNotNull(createdAccountDTO.getId());
    }

    @Test
    @DisplayName("Should return an error message when account holder field is null")
    public void testCreateAccount_AccountHolderIsNull() {
        validBankAccountDTO.setAccountHolder(null);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.createAccount(validBankAccountDTO);
        });

        assertEquals("Account holder is required", thrown.getMessage());
    }

    @Test
    @DisplayName("Should return an error message when global balance is a negative number")
    public void testCreateAccount_InvalidBalance() {
        validBankAccountDTO.setGlobalBalance(-500.0);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.createAccount(validBankAccountDTO);
        });

        assertEquals("Global balance must be a positive number", thrown.getMessage());
    }

    @Test
    @DisplayName("Should return an error message when account number is not unique")
    public void testCreateAccount_AccountNumberNotUnique() {
        Mockito.when(bankAccountRepository.existsByAccountNumber(validBankAccountDTO.getAccountNumber())).thenReturn(true);

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            bankAccountService.createAccount(validBankAccountDTO);
        });

        assertEquals("Account number already exists", thrown.getMessage());
    }

    @Test
    @DisplayName("Should return all accounts with data matching the database")
    public void testGetAllAccounts_Success() {
        BankAccount bankAccount1 = new BankAccount("12345", "1000008","John Doe", 1000.0,new ArrayList<>());
        BankAccount bankAccount2 = new BankAccount("67890", "1000009", "Jane Doe", 2000.0, new ArrayList<>());
        List<BankAccount> bankAccounts = Arrays.asList(bankAccount1, bankAccount2);

        Mockito.when(bankAccountRepository.findAll()).thenReturn(bankAccounts);
        List<BankAccountDTO> accountsDTO = bankAccountService.getAllAccounts();

        assertNotNull(accountsDTO);
        assertEquals(2, accountsDTO.size());
        assertEquals("John Doe", accountsDTO.get(0).getAccountHolder());
        assertEquals("Jane Doe", accountsDTO.get(1).getAccountHolder());
    }

    @Test
    @DisplayName("Should return an empty list when no accounts are found")
    public void testGetAllAccounts_Empty() {
        Mockito.when(bankAccountRepository.findAll()).thenReturn(Collections.emptyList());

        List<BankAccountDTO> accountsDTO = bankAccountService.getAllAccounts();

        assertTrue(accountsDTO.isEmpty());
    }

    @Test
    @DisplayName("Should assert true when the unique account number equals false. If assertion is false, then " +
            "account number is unique")
    public void testIsAccountNumberUnique_True() {
        Mockito.when(bankAccountRepository.existsByAccountNumber("12345")).thenReturn(false);

        boolean result = bankAccountService.isAccountNumberUnique("12345");

        assertTrue(result);
    }

    @Test
    @DisplayName("Should assert false when the unique account number equals true (already exists)")
    public void testIsAccountNumberUnique_False() {
        Mockito.when(bankAccountRepository.existsByAccountNumber("12345")).thenReturn(true);

        boolean result = bankAccountService.isAccountNumberUnique("12345");

        assertFalse(result);
    }

}


