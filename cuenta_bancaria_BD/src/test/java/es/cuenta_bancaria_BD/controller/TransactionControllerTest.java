package es.cuenta_bancaria_BD.controller;
import es.cuenta_bancaria_BD.controllers.TransactionController;
import es.cuenta_bancaria_BD.dto.TransactionDTO;
import es.cuenta_bancaria_BD.service.ITransactionService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest {
    @Mock
    private ITransactionService transaccionServicio;

    @InjectMocks
    private TransactionController transactionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testListTransaction_Success() throws Exception {
        // Datos de prueba
        TransactionDTO t1 = new TransactionDTO("1", BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        TransactionDTO t2 = new TransactionDTO("2", BigDecimal.valueOf(200), "RETIRO", BigDecimal.ONE, "123");
        List<TransactionDTO> transactions = Arrays.asList(t1, t2);
        when(transaccionServicio.listarTransacciones()).thenReturn(transactions);
        mockMvc.perform(get("/api/transacciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idTransaccion").value("1"))
                .andExpect(jsonPath("$[0].tipo").value("DEPOSITO"))
                .andExpect(jsonPath("$[1].idTransaccion").value("2"))
                .andExpect(jsonPath("$[1].tipo").value("RETIRO"));
    }

    @Test
    void testListTransaction_NoContent() throws Exception {
        when(transaccionServicio.listarTransacciones()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/transacciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    @Test
    void testGetTransactionById_Found() throws Exception {
        String transactionId = "1";
        TransactionDTO t1 = new TransactionDTO(transactionId, BigDecimal.valueOf(100), "DEPOSITO", BigDecimal.ZERO, "123");
        when(transaccionServicio.obtenerTransaccionPorId(transactionId)).thenReturn(t1);
        mockMvc.perform(get("/api/transacciones/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTransaccion").value("1"))
                .andExpect(jsonPath("$.tipo").value("DEPOSITO"))
                .andExpect(jsonPath("$.monto").value(100));
    }

    @Test
    void testGetTransactionById_NotFound() throws Exception {
        String transactionId = "999";
        when(transaccionServicio.obtenerTransaccionPorId(transactionId)).thenReturn(null);
        mockMvc.perform(get("/api/transacciones/{id}", transactionId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetTransactionById_InvalidId() throws Exception {
        String invalidId = "";
        when(transaccionServicio.obtenerTransaccionPorId(invalidId)).thenReturn(null);
        mockMvc.perform(get("/api/transacciones/{id}", invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
