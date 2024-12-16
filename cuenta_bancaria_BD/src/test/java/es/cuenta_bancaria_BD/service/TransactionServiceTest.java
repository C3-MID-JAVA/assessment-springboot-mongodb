package es.cuenta_bancaria_BD.service;
import es.cuenta_bancaria_BD.dto.TransactionDTO;
import es.cuenta_bancaria_BD.mapper.TransactionMapper;
import es.cuenta_bancaria_BD.model.Transaction;
import es.cuenta_bancaria_BD.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class TransactionServiceTest extends RuntimeException{

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper dtoMapper;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        openMocks(this); // Inicializar mocks
    }

    @Test
    void testListTransactions() {
        Transaction t1 = new Transaction("1", BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        Transaction t2 = new Transaction("2", BigDecimal.valueOf(200), "RETIRO", BigDecimal.ONE, "123");

        TransactionDTO t1Dto = new TransactionDTO("1", BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        TransactionDTO t2Dto = new TransactionDTO("2", BigDecimal.valueOf(200), "RETIRO", BigDecimal.ONE, "123");

        when(transactionRepository.findAll()).thenReturn(Arrays.asList(t1, t2));
        when(dtoMapper.toDto(t1)).thenReturn(t1Dto);
        when(dtoMapper.toDto(t2)).thenReturn(t2Dto);
        List<TransactionDTO> transactions = transactionService.listarTransacciones();
        assertEquals(2, transactions.size());
        assertEquals("DEPOSITO", transactions.get(0).getTipo()); // Línea problemática
        assertEquals(BigDecimal.ONE, transactions.get(1).getCosto());
    }

    @Test
    void testGetTransactionById_Found() {

        String transactionId = "1";
        Transaction t1 = new Transaction(transactionId, BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        TransactionDTO t1Dto = new TransactionDTO(transactionId, BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(t1));
        when(dtoMapper.toDto(t1)).thenReturn(t1Dto);
        TransactionDTO transaction = transactionService.obtenerTransaccionPorId(transactionId);
        assertNotNull(transaction, "La transacción no debe ser nula");
        assertEquals("DEPOSITO", transaction.getTipo(), "El tipo debe ser DEPOSITO");
        assertEquals(BigDecimal.ZERO, transaction.getCosto(), "El costo debe ser 0");
        verify(transactionRepository, times(1)).findById(transactionId);
        verify(dtoMapper, times(1)).toDto(t1);
    }

    @Test
    void testGetTransactionById_NotFound() {
        String transactionId = "999";
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());
        TransactionDTO transaction = transactionService.obtenerTransaccionPorId(transactionId);
        assertNull(transaction, "La transacción debe ser nula si no se encuentra");
        verify(transactionRepository, times(1)).findById(transactionId);
        verify(dtoMapper, times(0)).toDto(any(Transaction.class)); // No se debe llamar al mapper
    }
}
