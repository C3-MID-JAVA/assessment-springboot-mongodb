package es.cuenta_bancaria_BD.repository;
import es.cuenta_bancaria_BD.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setMonto(BigDecimal.valueOf(1000.0)); // Utiliza BigDecimal para el monto
        transaction.setTipo("Depósito");
        transaction.setCosto(BigDecimal.valueOf(5.0)); // Utiliza BigDecimal para el costo
        transaction.setIdCuenta("some-account-id");
    }

    @Test
    public void testSaveTransaction() {
        Transaction savedTransaction = transactionRepository.save(transaction);
        assertNotNull(savedTransaction.getIdTransaccion()); // Verifica que se haya asignado un ID
        assertEquals(transaction.getMonto(), savedTransaction.getMonto()); // Verifica los datos
        assertEquals(transaction.getTipo(), savedTransaction.getTipo());
        assertEquals(transaction.getCosto(), savedTransaction.getCosto());
        assertEquals(transaction.getIdCuenta(), savedTransaction.getIdCuenta());
    }

    @Test
    public void testFindTransactionById() {
        Transaction savedTransaction = transactionRepository.save(transaction);
        Transaction foundTransaction = transactionRepository.findById(savedTransaction.getIdTransaccion()).orElse(null);
        assertNotNull(foundTransaction);
        assertEquals(savedTransaction.getMonto(), foundTransaction.getMonto());
        assertEquals(savedTransaction.getTipo(), foundTransaction.getTipo());
        assertEquals(savedTransaction.getCosto(), foundTransaction.getCosto());
        assertEquals(savedTransaction.getIdCuenta(), foundTransaction.getIdCuenta());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionRepository.delete(savedTransaction);
        assertFalse(transactionRepository.findById(savedTransaction.getIdTransaccion()).isPresent()); // Verifica que la transacción haya sido eliminada
    }
}