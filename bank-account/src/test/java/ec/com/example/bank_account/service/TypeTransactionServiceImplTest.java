package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.TypeTransactionRequestDTO;
import ec.com.example.bank_account.dto.TypeTransactionResponseDTO;
import ec.com.example.bank_account.entity.TypeTransaction;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.TypeTransactionMapper;
import ec.com.example.bank_account.repository.TypeTransactionRepository;
import ec.com.example.bank_account.service.impl.TypeTransactionServiceImpl;
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
public class TypeTransactionServiceImplTest {

    @Mock
    private TypeTransactionRepository typeTransactionRepository;

    @Mock
    private TypeTransactionMapper typeTransactionMapper;

    @InjectMocks
    private TypeTransactionServiceImpl typeTransactionService;

    private TypeTransaction typeTransaction;
    private TypeTransactionRequestDTO typeTransactionRequestDTO;
    private TypeTransactionResponseDTO typeTransactionResponseDTO;

    @BeforeEach
    public void setUp() {
        typeTransaction = new TypeTransaction();
        typeTransaction.setType("Deposit from branch");
        typeTransaction.setDescription("Deposit from branch.");
        typeTransaction.setTransactionCost(true);
        typeTransaction.setDiscount(false);
        typeTransaction.setValue(new BigDecimal(1));
        typeTransaction.setStatus("ACTIVE");

        typeTransactionRequestDTO = new TypeTransactionRequestDTO("Deposit from branch", "Deposit from branch.",
                new BigDecimal(1), true,false, "ACTIVE");

        typeTransactionResponseDTO = new TypeTransactionResponseDTO("Deposit from branch", "Deposit from branch.",
                new BigDecimal(1), true,false, "ACTIVE");
    }

    @Test
    void createTypeTransaction_ShouldReturnCreatedEntity_WhenTypeTransactionIsCreatedSuccessfully() {
        when(typeTransactionMapper.mapToEntity(typeTransactionRequestDTO)).thenReturn(typeTransaction);
        when(typeTransactionRepository.save(typeTransaction)).thenReturn(typeTransaction);
        when(typeTransactionMapper.mapToDTO(typeTransaction)).thenReturn(typeTransactionResponseDTO);

        TypeTransactionResponseDTO response = typeTransactionService.createTypeTransaction(typeTransactionRequestDTO);

        assertNotNull(response);
        assertEquals(typeTransactionResponseDTO, response);
        verify(typeTransactionRepository, times(1)).save(typeTransaction);
        verify(typeTransactionMapper, times(1)).mapToDTO(typeTransaction);
    }

    @Test
    void getAllTypeTransactions_ShouldReturnAllTypeTransactions_WhenTypeTransactionsAreFound() {
        List<TypeTransaction> typeTransactions = List.of(typeTransaction);
        List<TypeTransactionResponseDTO> typeTransactionResponseDTOs = List.of(typeTransactionResponseDTO);

        when(typeTransactionRepository.findAll()).thenReturn(typeTransactions);
        when(typeTransactionMapper.mapToDTO(any(TypeTransaction.class))).thenAnswer(invocation -> {
            TypeTransaction typeTransaction = invocation.getArgument(0);
            return new TypeTransactionResponseDTO( typeTransaction.getType(), typeTransaction.getDescription(),
                    typeTransaction.getValue(), typeTransaction.getTransactionCost(), typeTransaction.getDiscount(), typeTransaction.getStatus());
        });

        List<TypeTransactionResponseDTO> result = typeTransactionService.getAllTypeTransactions();

        assertNotNull(result);
        assertEquals(typeTransactionResponseDTOs.size(), result.size());
        verify(typeTransactionRepository, times(1)).findAll();
        verify(typeTransactionMapper, times(1)).mapToDTO(any(TypeTransaction.class));
    }

    @Test
    void getAllTypeTransactions_ShouldThrowExceptionWhenNoTypeTransactionsFound() {
        when(typeTransactionRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> typeTransactionService.getAllTypeTransactions());
        assertEquals("No typesTransaction records found.", exception.getMessage());
    }
}