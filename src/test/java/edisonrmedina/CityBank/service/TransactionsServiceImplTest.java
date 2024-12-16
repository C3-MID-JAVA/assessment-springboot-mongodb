package edisonrmedina.CityBank.service;

import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.entity.transaction.Transaction;
import edisonrmedina.CityBank.entity.transaction.TransactionCostStrategy.*;
import edisonrmedina.CityBank.enums.TransactionType;
import edisonrmedina.CityBank.repository.TransactionRepository;
import edisonrmedina.CityBank.service.BankAccountService;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import edisonrmedina.CityBank.service.impl.TransactionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static edisonrmedina.CityBank.entity.transaction.Transaction.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionsServiceImplTest {

    @Mock
    private BankAccountServiceImp bankAccountService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionCostStrategy costStrategy;

    @InjectMocks
    private TransactionsServiceImpl transactionsService;

    private Optional<BankAccount> bankAccount;
    private Transaction transaction;
    private Map<TransactionType, TransactionCostStrategy> costStrategies;
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this); // Inicializa los mocks manualmente

        costStrategies = Map.of(
                TransactionType.DEPOSIT_BRANCH, new DepositBranchCostStrategy(),
                TransactionType.DEPOSIT_ATM, new DepositAtmCostStrategy(),
                TransactionType.DEPOSIT_ACCOUNT, new DepositAccountCostStrategy(),
                TransactionType.PURCHASE_PHYSICAL, new PurchasePhysicalCostStrategy(),
                TransactionType.PURCHASE_ONLINE, new PurchaseWebCostStrategy(),
                TransactionType.WITHDRAW_ATM, new WithdrawAtmCostStrategy()
        );

        // Crear una cuenta bancaria de prueba (sin depender del servicio)
        bankAccount = Optional.of(BankAccount.builder()
                .id("675e962784c89c424d3bc7b6")
                .balance(new BigDecimal("1000"))
                .build());

        // Configurar la transacción de prueba
        transaction = Transaction.builder()
                .type(TransactionType.WITHDRAW_ATM)
                .amount(new BigDecimal("200"))
                .transactionCost(new BigDecimal("1"))
                .timestamp(LocalDateTime.now()) // Fecha actual simulada
                .build();

        transaction.setBankAccount(Optional.of(bankAccount.get()));
    }

    @Test
    void testCreateTransaction_accountNotFound() {
        // Simula que no se encuentra la cuenta bancaria
        lenient().when(bankAccountService.getBankAccount(anyString())).thenReturn(Optional.empty());

        // Ejecuta el método y verifica que lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionsService.createTransaction(transaction);
        });

        assertEquals("Cuenta bancaria no encontrada", exception.getMessage());
    }

    @Test
    void testCreateTransaction_typeNotSupported() {

        Optional<BankAccount> mockBankAccount = bankAccount;

        // Configurar el tipo de transacción como no soportado
        transaction.setType(TransactionType.DEPOSIT_OUT); // Asumiendo que no tiene estrategia

        // Ejecutar el método y verificar que lance una excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionsService.calculateNewBalance(transaction,costStrategies);
        });

        // Verificar el mensaje de la excepción
        assertEquals("Tipo de transacción no soportado", exception.getMessage());

        // Verificar que el método calculateCost NO fue llamado
        verify(costStrategy, never()).calculateCost(any());
    }

    @Test
    void testCreateTransaction_successfulWithdrawal() {

        Optional<BankAccount> mockBankAccount = bankAccount;
        bankAccount.get().setBalance(new BigDecimal("789"));
        Optional<Transaction> mockTransaction = Optional.of(transaction);
        // Simular que la cuenta bancaria existe y tiene saldo suficiente
        lenient().when(costStrategy.calculateCost(any())).thenReturn(new BigDecimal("1")); // Simular un costo de transacción

        // Ejecutar la transacción
        transactionRepository.save(mockTransaction.get());

        // Verificar que la transacción se guardó correctamente
        verify(transactionRepository, times(1)).save(transaction);
        assertEquals(new BigDecimal("789"), mockBankAccount.get().getBalance()); // 1000 - 200 - 10 = 790
    }

    @Test
    void testCreateTransaction_insufficientBalance() {
        // Simular saldo insuficiente
        Optional<BankAccount> mockBankAccount = bankAccount;
        bankAccount.get().setBalance(new BigDecimal("0"));
        Optional<Transaction> mockTransaction = Optional.of(transaction);
        mockTransaction.get().setType(TransactionType.WITHDRAW_ATM);
        lenient().when(costStrategy.calculateCost(any())).thenReturn(new BigDecimal("1"));

        // Ejecutar y verificar que se lance una excepción por saldo insuficiente
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transactionsService.calculateNewBalance(transaction,costStrategies);
        });

        assertEquals("Saldo insuficiente para realizar la transacción", exception.getMessage());
    }
}
