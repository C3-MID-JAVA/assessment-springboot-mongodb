package es.cuenta_bancaria_BD.controller;
import es.cuenta_bancaria_BD.controllers.AccountController;
import es.cuenta_bancaria_BD.dto.AccountDTO;
import es.cuenta_bancaria_BD.service.IAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    @Mock
    private IAccountService cuentaServicio;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testListAccount_Success() throws Exception {
        // Datos de prueba
        AccountDTO a1 = new AccountDTO("1", "Juan Perez", BigDecimal.valueOf(1000), null);
        AccountDTO a2 = new AccountDTO("2", "Maria Lopez", BigDecimal.valueOf(2000), null);
        List<AccountDTO> accounts = Arrays.asList(a1, a2);

        when(cuentaServicio.listarCuentas()).thenReturn(accounts);
        mockMvc.perform(get("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idCuenta").value("1"))
                .andExpect(jsonPath("$[0].titular").value("Juan Perez"))
                .andExpect(jsonPath("$[1].idCuenta").value("2"))
                .andExpect(jsonPath("$[1].titular").value("Maria Lopez"));
    }

    @Test
    void testListAccount_NoContent() throws Exception {
        when(cuentaServicio.listarCuentas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchAccountById_Found() throws Exception {
        // Datos de prueba
        String accountId = "1";
        AccountDTO a1 = new AccountDTO(accountId, "Juan Perez", BigDecimal.valueOf(1000), null);

        when(cuentaServicio.obtenerCuentaPorId(accountId)).thenReturn(a1);
        mockMvc.perform(get("/api/cuentas/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCuenta").value("1"))
                .andExpect(jsonPath("$.titular").value("Juan Perez"))
                .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    void testSearchAccountById_NotFound() throws Exception {
        String accountId = "999";

        when(cuentaServicio.obtenerCuentaPorId(accountId)).thenReturn(null);
        mockMvc.perform(get("/api/cuentas/{id}", accountId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateAccount_Success() throws Exception {
        // Datos de prueba
        AccountDTO a1 = new AccountDTO(null, "Juan Perez", BigDecimal.valueOf(1000), null);
        AccountDTO savedAccount = new AccountDTO("1", "Juan Perez", BigDecimal.valueOf(1000), null);

        when(cuentaServicio.crearCuenta(a1)).thenReturn(savedAccount);
        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "titular": "Juan Perez",
                        "saldo": 1000
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idCuenta").value("1"))
                .andExpect(jsonPath("$.titular").value("Juan Perez"))
                .andExpect(jsonPath("$.saldo").value(1000));
    }

    @Test
    void testCreateAccount_Failure() throws Exception {
        AccountDTO a1 = new AccountDTO(null, "Juan Perez", BigDecimal.valueOf(1000), null);

        when(cuentaServicio.crearCuenta(a1)).thenReturn(null);
        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "titular": "Juan Perez",
                        "saldo": 1000
                    }
                    """))
                .andExpect(status().isNotModified());
    }

    @Test
    void testMakeTransaction_Success() throws Exception {
        // Datos de prueba
        String accountId = "1";
        BigDecimal monto = BigDecimal.valueOf(500);
        String tipo = "DEPOSITO";
        AccountDTO updatedAccount = new AccountDTO(accountId, "Juan Perez", BigDecimal.valueOf(1500), null);

        when(cuentaServicio.realizarTransaccion(accountId, monto, tipo)).thenReturn(updatedAccount);
        mockMvc.perform(post("/api/cuentas/{id}/transacciones", accountId)
                        .param("monto", "500")
                        .param("tipo", "DEPOSITO")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCuenta").value("1"))
                .andExpect(jsonPath("$.saldo").value(1500));
    }
}
