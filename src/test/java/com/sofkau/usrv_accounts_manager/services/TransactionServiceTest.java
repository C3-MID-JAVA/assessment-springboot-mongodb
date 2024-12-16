package com.sofkau.usrv_accounts_manager.services;

import com.sofkau.usrv_accounts_manager.Utils.ConstansTrType;
import com.sofkau.usrv_accounts_manager.dto.AccountDTO;
import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.dto.TransactionDTO;
import com.sofkau.usrv_accounts_manager.model.AccountModel;
import com.sofkau.usrv_accounts_manager.model.CardModel;
import com.sofkau.usrv_accounts_manager.model.abstracts.TransactionModel;
import com.sofkau.usrv_accounts_manager.model.classes.AtmTransaction;
import com.sofkau.usrv_accounts_manager.model.classes.BranchDeposit;
import com.sofkau.usrv_accounts_manager.repository.AccountRepository;
import com.sofkau.usrv_accounts_manager.repository.CardRepository;
import com.sofkau.usrv_accounts_manager.repository.TransactionRepository;
import com.sofkau.usrv_accounts_manager.services.impl.AccountServiceImpl;
import com.sofkau.usrv_accounts_manager.services.impl.CardServiceImpl;
import com.sofkau.usrv_accounts_manager.services.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private TransactionModel transaction;
    private CardModel card;
    private AccountModel account;
    private AccountModel accountReceiver;
    private TransactionDTO transactionDTO;
    private CardDTO cardDTO;
    private AccountDTO accountDTO;
    private AccountDTO accountReceiverDTO;

    @BeforeEach
    void setUp() {
        card = new CardModel();
        account = new AccountModel();
        accountReceiver = new AccountModel();
        card.setCardNumber("123456789");
        account.setAccountNumber("123456789");
        accountReceiver.setAccountNumber("987654321");
        account.setBalance(BigDecimal.valueOf(1000));
        cardDTO = new CardDTO();
        cardDTO.setCardNumber("123456789");
        accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("123456789");
        accountReceiverDTO = new AccountDTO();
        accountReceiverDTO.setAccountNumber("987654321");
        transactionDTO = new TransactionDTO("Test Transaction", BigDecimal.valueOf(10),
                "ATM", BigDecimal.valueOf(0), accountDTO, cardDTO);

    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type ATM with operation type ATM_DEBIT")
    void createTransaction_success() throws Exception {
        transactionDTO.setOperationType(ConstansTrType.ATM_DEBIT);
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        transaction = new AtmTransaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionFee(BigDecimal.valueOf(1));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);

        TransactionDTO result = transactionService.createTransaction(transactionDTO);
        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(1), result.getTransactionFee());
    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type ATM with operation type ATM_DEPOSITO")
    void createTransaction_success_ATM() throws Exception {
        transactionDTO.setOperationType(ConstansTrType.ATM_DEPOSIT);
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        transaction = new AtmTransaction();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionFee(BigDecimal.valueOf(2));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(2), result.getTransactionFee());

    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type BRANCH_DEPOSIT ")
    void createTransaction_success_BRANCH_DEPOSIT() throws Exception {
        transactionDTO.setTransactionType(ConstansTrType.BRANCH_DEPOSIT);
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        transaction = new BranchDeposit();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionFee(BigDecimal.valueOf(0));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(0), result.getTransactionFee());

    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type BETWEEN_ACCOUNT ")
    void createTransaction_success_BETWEEN_ACCOUNT() throws Exception {
        transactionDTO.setTransactionType(ConstansTrType.BETWEEN_ACCOUNT);
        transactionDTO.setAccountReceiver(accountReceiver);
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(accountRepository.findByAccountNumber(accountReceiverDTO.getAccountNumber()))
                .thenReturn(Optional.of(accountReceiver));

        transaction = new BranchDeposit();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());

        transaction.setTransactionFee(BigDecimal.valueOf(1.5));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(1.5), result.getTransactionFee());

    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type STORE_PURCHASE ")
    void createTransaction_success_STORE_PURCHASE() throws Exception {
        transactionDTO.setTransactionType(ConstansTrType.STORE_PURCHASE);
        transactionDTO.setBranchName("TEST");
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        transaction = new BranchDeposit();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionFee(BigDecimal.valueOf(0));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(0), result.getTransactionFee());

    }

    @Test
    @DisplayName("Should create a new transaction when account and card exist and is type WEB_PURCHASE ")
    void createTransaction_success_WEB_PURCHASE() throws Exception {
        transactionDTO.setTransactionType(ConstansTrType.WEB_PURCHASE);
        transactionDTO.setWebsite("website");
        when(accountRepository.findByAccountNumber(accountDTO.getAccountNumber()))
                .thenReturn(Optional.of(account));
        when(cardRepository.findByCardNumber(cardDTO.getCardNumber()))
                .thenReturn(Optional.of(card));

        transaction = new BranchDeposit();
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setAccount(account);
        transaction.setCard(card);
        transaction.setTransactionType(transactionDTO.getTransactionType());
        transaction.setTransactionFee(BigDecimal.valueOf(0));
        when(transactionRepository.save(any(TransactionModel.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);

        assertNotNull(result);
        assertEquals(transactionDTO.getDescription(), result.getDescription());
        assertEquals(BigDecimal.valueOf(0), result.getTransactionFee());

    }
}