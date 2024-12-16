package es.cuenta_bancaria_BD.service;
import es.cuenta_bancaria_BD.dto.AccountDTO;
import es.cuenta_bancaria_BD.exception.SaldoInsuficienteException;
import es.cuenta_bancaria_BD.mapper.AccountMapper;
import es.cuenta_bancaria_BD.model.Account;
import es.cuenta_bancaria_BD.model.Transaction;
import es.cuenta_bancaria_BD.repository.AccountRepository;
import es.cuenta_bancaria_BD.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


public class AccountServiceTests {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountMapper dtoMapper;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }
    @Test
    void testListarCuentas_Success() {
        List<Account> cuentas = List.of(
                new Account("1", "Carlos", BigDecimal.valueOf(1000), new ArrayList<>()),
                new Account("2", "Ana", BigDecimal.valueOf(2000), new ArrayList<>())
        );
        List<AccountDTO> cuentasDTO = List.of(
                new AccountDTO("1", "Carlos", BigDecimal.valueOf(1000), new ArrayList<>()),
                new AccountDTO("2", "Ana", BigDecimal.valueOf(2000), new ArrayList<>())
        );

        // Configuración de mocks
        when(accountRepository.findAll()).thenReturn(cuentas);
        when(dtoMapper.toDto(cuentas.get(0))).thenReturn(cuentasDTO.get(0));
        when(dtoMapper.toDto(cuentas.get(1))).thenReturn(cuentasDTO.get(1));
        // Ejecución del método
        List<AccountDTO> resultado = accountService.listarCuentas();
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(accountRepository, times(1)).findAll();
        verify(dtoMapper, times(2)).toDto(any(Account.class));
    }

    @Test
    void testObtenerCuentaPorId_Success() {
        Account cuenta = new Account("1", "Carlos", BigDecimal.valueOf(1000), null);
        AccountDTO cuentaDTO = new AccountDTO("1", "Carlos", BigDecimal.valueOf(1000), null);

        when(accountRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(dtoMapper.toDto(cuenta)).thenReturn(cuentaDTO);
        AccountDTO resultado = accountService.obtenerCuentaPorId("1");
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getTitular());
        verify(accountRepository, times(1)).findById("1");
        verify(dtoMapper, times(1)).toDto(cuenta);
    }

    @Test
    void testObtenerCuentaPorId_CuentaNoEncontrada() {
        // Configuración de mocks
        when(accountRepository.findById("1")).thenReturn(Optional.empty());
        AccountDTO resultado = accountService.obtenerCuentaPorId("1");
        assertNull(resultado);
        verify(accountRepository, times(1)).findById("1");
        verify(dtoMapper, never()).toDto(any());
    }

    @Test
    public void testCrearCuenta() {
        // Datos de prueba
        AccountDTO cuentaDTO = new AccountDTO("1", "Carlos", BigDecimal.valueOf(1000), new ArrayList<>());
        Account cuenta = new Account("1", "Carlos", BigDecimal.valueOf(1000), new ArrayList<>());

        when(dtoMapper.toEntity(cuentaDTO)).thenReturn(cuenta);
        when(accountRepository.save(cuenta)).thenReturn(cuenta);
        when(dtoMapper.toDto(cuenta)).thenReturn(cuentaDTO);
        AccountDTO resultado = accountService.crearCuenta(cuentaDTO);
        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getTitular());
        assertEquals(BigDecimal.valueOf(1000), resultado.getSaldo());
        verify(accountRepository, times(1)).save(cuenta);
        verify(dtoMapper, times(1)).toDto(cuenta);
        verify(dtoMapper, times(1)).toEntity(cuentaDTO);
    }

    @Test
    public void testMocksAreInitialized() {
        assertNotNull(accountRepository, "AccountRepository no fue inicializado");
        assertNotNull(transactionRepository, "TransactionRepository no fue inicializado");
        assertNotNull(dtoMapper, "AccountMapper no fue inicializado");
        assertNotNull(accountService, "AccountService no fue inicializado");
    }

    @Test
    void testRealizarTransaccion_Success() {
        Account cuenta = new Account("1", "Carlos", BigDecimal.valueOf(1000), new ArrayList<>());
        AccountDTO cuentaDTO = new AccountDTO("1", "Carlos", BigDecimal.valueOf(900), new ArrayList<>());
        Transaction transaccion = new Transaction("1", BigDecimal.valueOf(100),"RETIRO_CAJERO",  BigDecimal.ONE, "1");

        when(accountRepository.findById("1")).thenReturn(Optional.of(cuenta));
        when(dtoMapper.toDto(cuenta)).thenReturn(cuentaDTO);
        when(accountRepository.save(any(Account.class))).thenReturn(cuenta);
        AccountDTO resultado = accountService.realizarTransaccion("1", BigDecimal.valueOf(100), "RETIRO_CAJERO");
        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(900), resultado.getSaldo()); // Saldo después de la transacción
        verify(accountRepository, times(1)).save(cuenta);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(dtoMapper, times(1)).toDto(cuenta);
    }

    @Test
    void testRealizarTransaccion_SaldoInsuficiente() {
        Account cuenta = new Account("1", "Carlos", BigDecimal.valueOf(50), new ArrayList<>());
        when(accountRepository.findById("1")).thenReturn(Optional.of(cuenta));
        assertThrows(SaldoInsuficienteException.class,
                () -> accountService.realizarTransaccion("1", BigDecimal.valueOf(100), "RETIRO_CAJERO"));
        verify(accountRepository, times(1)).findById("1");
        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());

    }

}
