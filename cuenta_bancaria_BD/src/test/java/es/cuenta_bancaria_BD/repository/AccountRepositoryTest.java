package es.cuenta_bancaria_BD.repository;
import es.cuenta_bancaria_BD.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.math.BigDecimal;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setTitular("Juan Perez");
        account.setSaldo(BigDecimal.valueOf(1000.0)); // Utiliza BigDecimal para el saldo
        account.setTransacciones(Collections.emptyList()); // Inicializa la lista de transacciones vacía
    }

    @Test
    public void testSaveAccount() {
        Account savedAccount = accountRepository.save(account);
        assertNotNull(savedAccount.getIdCuenta()); // Verifica que se haya asignado un ID
        assertEquals(account.getTitular(), savedAccount.getTitular()); // Verifica el titular
        assertEquals(account.getSaldo(), savedAccount.getSaldo()); // Verifica el saldo
    }

    @Test
    public void testFindAccountById() {
        Account savedAccount = accountRepository.save(account);
        Account foundAccount = accountRepository.findById(savedAccount.getIdCuenta()).orElse(null);
        assertNotNull(foundAccount);
        assertEquals(savedAccount.getTitular(), foundAccount.getTitular());
        assertEquals(savedAccount.getSaldo(), foundAccount.getSaldo());
    }

    @Test
    public void testDeleteAccount() {
        Account savedAccount = accountRepository.save(account);
        accountRepository.delete(savedAccount);
        assertFalse(accountRepository.findById(savedAccount.getIdCuenta()).isPresent()); // Verifica que la cuenta haya sido eliminada
    }
}