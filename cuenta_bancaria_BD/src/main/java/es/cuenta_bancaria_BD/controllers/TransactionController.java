package es.cuenta_bancaria_BD.controllers;


import es.cuenta_bancaria_BD.dto.TransactionDTO;
import es.cuenta_bancaria_BD.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Transaction Management", description = "Endpoints for managing bank transaction")
@RestController
@RequestMapping("/api/transacciones")
public class TransactionController {

    @Autowired
    private ITransactionService transaccionServicio;

    @Operation(summary = "List Transaction", description = "List of transaction created.")
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> listTransaction(){
        var response = transaccionServicio.listarTransacciones();
        return response.isEmpty()?
                ResponseEntity.noContent().build():
                ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get transaction by ID", description = "Fetches an transaction using its ID")
    @GetMapping("{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable String id){
        var response = transaccionServicio.obtenerTransaccionPorId(id);
        return response != null?
                ResponseEntity.ok().body(response):
                ResponseEntity.noContent().build();
    }
}
