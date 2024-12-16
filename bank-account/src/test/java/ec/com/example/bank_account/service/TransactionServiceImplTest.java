package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.TransactionRequestDTO;
import ec.com.example.bank_account.dto.TransactionResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.entity.Account;
import ec.com.example.bank_account.entity.Transaction;
import ec.com.example.bank_account.entity.TypeTransaction;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.exception.TransactionRejectedException;
import ec.com.example.bank_account.mapper.TransactionMapper;
import ec.com.example.bank_account.repository.AccountRepository;
import ec.com.example.bank_account.repository.TransactionRepository;
import ec.com.example.bank_account.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransactionServiceImplTest {
    
    @Mock
    private TransactionMapper transactionMapper;
    
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;
    private Account account;
    private TypeTransaction typeTransaction;
    private TransactionRequestDTO transactionRequestDTO;
    private TransactionResponseDTO transactionResponseDTO;
    private AccountResponseDTO accountResponseDTO;
    private TypeTransactionResponseDTO typeTransactionResponseDTO;

    @BeforeEach
    public void setUp() {
        UserResponseDTO userResponse = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
        TypeAccountResponseDTO typeAccountResponse = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");

        accountResponseDTO = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponse, typeAccountResponse);

        account = new Account();
        account.setNumber("2200000000");
        account.setAvailableBalance(new BigDecimal(100));
        account.setRetainedBalance(new BigDecimal(0));
        account.setStatus("ACTIVE");

        typeTransaction = new TypeTransaction();
        typeTransaction.setValue(new BigDecimal(100));
        typeTransaction.setTransactionCost(false);
        typeTransaction.setDiscount(true);

        transaction = new Transaction();
        transaction.setDetails("transaction made in Manabí.");
        transaction.setDate(new Date());
        transaction.setValue(new BigDecimal(100));
        transaction.setStatus("ACTIVE");
        transaction.setAccount(account);
        transaction.setTypeTransaction(typeTransaction);

        typeTransactionResponseDTO = new TypeTransactionResponseDTO("Deposit from branch",
                "Deposits made from a branch.", new BigDecimal(100), true, false, "ACTIVE");

        transactionRequestDTO = new TransactionRequestDTO(new BigDecimal(100), new Date(),
                "2200000000", "transaction made in Manabí.", "ACTIVE", "test");
        transactionResponseDTO = new TransactionResponseDTO(new BigDecimal(100), new Date(),
                "ACTIVE", accountResponseDTO, typeTransactionResponseDTO);
    }

    @Test
    void createTransaction_ShouldCreateTransactionSuccessfully() {
        transaction.getAccount().setAvailableBalance(new BigDecimal(250));
        when(transactionMapper.mapToEntity(transactionRequestDTO)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.mapToDTO(any(Transaction.class)))
                .thenReturn(new TransactionResponseDTO(
                        transaction.getValue(), transaction.getDate(), transaction.getStatus(),
                        new AccountResponseDTO(account.getNumber(), account.getAvailableBalance(),
                                new BigDecimal(0), account.getStatus(), null, null),
                        new TypeTransactionResponseDTO(typeTransaction.getDescription(), typeTransaction.getValue().toString(),
                                typeTransaction.getValue(), true, false, "ACTIVE")
                ));

        TransactionResponseDTO result = transactionService.createTransaction(transactionRequestDTO);

        assertNotNull(result);
        assertEquals(transaction.getValue(), result.getValue());
        assertEquals("ACTIVE", result.getStatus());
        verify(transactionRepository, times(1)).save(transaction);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void createTransaction_ShouldThrowExceptionWhenAccountIsInactive() {
        account.setStatus("INACTIVE");
        when(transactionMapper.mapToEntity(transactionRequestDTO)).thenReturn(transaction);

        TransactionRejectedException exception = assertThrows(
                TransactionRejectedException.class,
                () -> transactionService.createTransaction(transactionRequestDTO)
        );

        assertEquals("Inactive or invalid account.", exception.getMessage());
    }

    @Test
    void createTransaction_ShouldThrowExceptionWhenTransactionCostIsHigherThanAmount() {
        transaction.setValue(new BigDecimal(150));
        transaction.getTypeTransaction().setValue(new BigDecimal(100));
        transaction.getTypeTransaction().setTransactionCost(false);
        when(transactionMapper.mapToEntity(transactionRequestDTO)).thenReturn(transaction);

        TransactionRejectedException exception = assertThrows(
                TransactionRejectedException.class,
                () -> transactionService.createTransaction(transactionRequestDTO)
        );

        assertEquals("The transaction cost is higher than the amount to be sent.", exception.getMessage());
    }

    @Test
    void createTransaction_ShouldThrowExceptionWhenFundsAreInsufficient() {
        account.setAvailableBalance(new BigDecimal(100));
        when(transactionMapper.mapToEntity(transactionRequestDTO)).thenReturn(transaction);

        TransactionRejectedException exception = assertThrows(
                TransactionRejectedException.class,
                () -> transactionService.createTransaction(transactionRequestDTO)
        );

        assertEquals("the account does not have sufficient funds.", exception.getMessage());
    }

    @Test
    void createTransaction_ShouldAddFundsWhenTransactionTypeDoesNotDiscount() {
        typeTransaction.setDiscount(false);
        transaction.setValue(new BigDecimal(50));
        BigDecimal initialBalance = account.getAvailableBalance();
        when(transactionMapper.mapToEntity(transactionRequestDTO)).thenReturn(transaction);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionMapper.mapToDTO(any(Transaction.class)))
                .thenReturn(new TransactionResponseDTO(
                        transaction.getValue(), transaction.getDate(), transaction.getStatus(),
                        new AccountResponseDTO(account.getNumber(), initialBalance.add(transaction.getValue()),
                                new BigDecimal(0), account.getStatus(), null, null),
                        new TypeTransactionResponseDTO(typeTransaction.getDescription(), typeTransaction.getValue().toString(),
                                typeTransaction.getValue(), false, false, "ACTIVE")
                ));

        TransactionResponseDTO result = transactionService.createTransaction(transactionRequestDTO);

        assertNotNull(result);
        assertEquals(initialBalance.add(transaction.getValue()), result.getAccount().getAvailableBalance());
        verify(transactionRepository, times(1)).save(transaction);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void getAllTransactions_ShouldReturnAllTransactions_WhenTransactionsAreFound() {
        List<Transaction> transactions = List.of(transaction);
        List<TransactionResponseDTO> transactionResponseDTOs = List.of(transactionResponseDTO);

        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionMapper.mapToDTO(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            return new TransactionResponseDTO( transaction.getValue(), transaction.getDate(), transaction.getStatus(),
                    accountResponseDTO, typeTransactionResponseDTO);
        });

        List<TransactionResponseDTO> result = transactionService.getAllTransactions();

        assertNotNull(result);
        assertEquals(transactionResponseDTOs.size(), result.size());
        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, times(1)).mapToDTO(any(Transaction.class));
    }

    @Test
    void getAllTransactions_ShouldThrowExceptionWhenNoTransactionsFound() {
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> transactionService.getAllTransactions());
        assertEquals("No transactions records found.", exception.getMessage());
    }
    
}