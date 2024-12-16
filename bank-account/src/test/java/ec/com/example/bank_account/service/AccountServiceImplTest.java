package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.AccountRequestDTO;
import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.entity.Account;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.AccountMapper;
import ec.com.example.bank_account.repository.AccountRepository;
import ec.com.example.bank_account.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountServiceImpl accountService;

    private Account account;
    private TypeAccountResponseDTO typeAccountResponseDTO;
    private UserResponseDTO userResponseDTO;
    private AccountRequestDTO accountRequestDTO;
    private AccountResponseDTO accountResponseDTO;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setNumber("2200000000");
        account.setAvailableBalance(new BigDecimal(100));
        account.setRetainedBalance(new BigDecimal(0));
        account.setStatus("ACTIVE");

        userResponseDTO = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");

        typeAccountResponseDTO = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");

        accountRequestDTO = new AccountRequestDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", "test", "test");
        accountResponseDTO = new AccountResponseDTO("2200000000", new BigDecimal(100),
                new BigDecimal(0), "ACTIVE", userResponseDTO, typeAccountResponseDTO);
    }

    @Test
    void createAccount_ShouldReturnCreatedEntity_WhenAccountIsCreatedSuccessfully() {
        when(accountMapper.mapToEntity(accountRequestDTO)).thenReturn(account);
        when(accountRepository.save(account)).thenReturn(account);
        when(accountMapper.mapToDTO(account)).thenReturn(accountResponseDTO);

        AccountResponseDTO response = accountService.createAccount(accountRequestDTO);

        assertNotNull(response);
        assertEquals(accountResponseDTO, response);
        verify(accountRepository, times(1)).save(account);
        verify(accountMapper, times(1)).mapToDTO(account);
    }

    @Test
    void getAllAccounts_ShouldReturnAllAccounts_WhenAccountsAreFound() {
        List<Account> accounts = List.of(account);
        List<AccountResponseDTO> accountResponseDTOs = List.of(accountResponseDTO);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.mapToDTO(any(Account.class))).thenAnswer(invocation -> {
            Account account = invocation.getArgument(0);
            return new AccountResponseDTO(account.getNumber(), account.getAvailableBalance(), account.getRetainedBalance(),
                    account.getStatus(), userResponseDTO, typeAccountResponseDTO);
        });

        List<AccountResponseDTO> result = accountService.getAllAccounts();

        assertNotNull(result);
        assertEquals(accountResponseDTOs.size(), result.size());
        verify(accountRepository, times(1)).findAll();
        verify(accountMapper, times(1)).mapToDTO(any(Account.class));
    }

    @Test
    void getAllAccounts_ShouldThrowExceptionWhenNoAccountsFound() {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> accountService.getAllAccounts());
        assertEquals("No accounts records found.", exception.getMessage());
    }

}
