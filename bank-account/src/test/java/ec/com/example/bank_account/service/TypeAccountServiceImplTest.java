package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.TypeAccountRequestDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.entity.TypeAccount;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.TypeAccountMapper;
import ec.com.example.bank_account.repository.TypeAccountRepository;
import ec.com.example.bank_account.service.impl.TypeAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
public class TypeAccountServiceImplTest {
    
    @Mock
    private TypeAccountRepository typeAccountRepository;

    @Mock
    private TypeAccountMapper typeAccountMapper;

    @InjectMocks
    private TypeAccountServiceImpl typeAccountService;

    private TypeAccount typeAccount;
    private TypeAccountRequestDTO typeAccountRequestDTO;
    private TypeAccountResponseDTO typeAccountResponseDTO;

    @BeforeEach
    public void setUp() {
        typeAccount = new TypeAccount();
        typeAccount.setType("Debit account");
        typeAccount.setDescription("User debit account.");
        typeAccount.setStatus("ACTIVE");

        typeAccountRequestDTO = new TypeAccountRequestDTO("Debit account", "User debit account.", "ACTIVE");

        typeAccountResponseDTO = new TypeAccountResponseDTO("Debit account", "User debit account.", "ACTIVE");
    }

    @Test
    void createTypeAccount_ShouldReturnCreatedEntity_WhenTypeAccountIsCreatedSuccessfully() {
        when(typeAccountMapper.mapToEntity(typeAccountRequestDTO)).thenReturn(typeAccount);
        when(typeAccountRepository.save(typeAccount)).thenReturn(typeAccount);
        when(typeAccountMapper.mapToDTO(typeAccount)).thenReturn(typeAccountResponseDTO);

        TypeAccountResponseDTO response = typeAccountService.createTypeAccount(typeAccountRequestDTO);

        assertNotNull(response);
        assertEquals(typeAccountResponseDTO, response);
        verify(typeAccountRepository, times(1)).save(typeAccount);
        verify(typeAccountMapper, times(1)).mapToDTO(typeAccount);
    }

    @Test
    void getAllTypeAccounts_ShouldReturnAllTypeAccounts_WhenTypeAccountsAreFound() {
        List<TypeAccount> typeAccounts = List.of(typeAccount);
        List<TypeAccountResponseDTO> typeAccountResponseDTOs = List.of(typeAccountResponseDTO);

        when(typeAccountRepository.findAll()).thenReturn(typeAccounts);
        when(typeAccountMapper.mapToDTO(any(TypeAccount.class))).thenAnswer(invocation -> {
            TypeAccount typeAccount = invocation.getArgument(0);
            return new TypeAccountResponseDTO( typeAccount.getType(), typeAccount.getDescription(), typeAccount.getStatus());
        });

        List<TypeAccountResponseDTO> result = typeAccountService.getAllTypeAccount();

        assertNotNull(result);
        assertEquals(typeAccountResponseDTOs.size(), result.size());
        verify(typeAccountRepository, times(1)).findAll();
        verify(typeAccountMapper, times(1)).mapToDTO(any(TypeAccount.class));
    }

    @Test
    void getAllTypeAccounts_ShouldThrowExceptionWhenNoTypeAccountsFound() {
        when(typeAccountRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> typeAccountService.getAllTypeAccount());
        assertEquals("No typesAccount records found.", exception.getMessage());
    }
}