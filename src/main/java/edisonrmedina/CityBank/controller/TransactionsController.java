package edisonrmedina.CityBank.controller;

import edisonrmedina.CityBank.dto.TransactionDTO;
import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.entity.transaction.Transaction;
import edisonrmedina.CityBank.mapper.Mapper;
import edisonrmedina.CityBank.service.BankAccountService;
import edisonrmedina.CityBank.service.TransactionsService;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import edisonrmedina.CityBank.service.impl.TransactionsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsServiceImpl transactionsService;
    private final BankAccountServiceImp bankAccountService;

    public TransactionsController(TransactionsServiceImpl transactionsServiceImp, BankAccountServiceImp bankAccountServiceImpl) {
        this.transactionsService = transactionsServiceImp;
        this.bankAccountService = bankAccountServiceImpl;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        return ResponseEntity.ok(transactionsService.getAllTransactions());
    }

    @PostMapping("/{accountId}/transactions")
    public ResponseEntity<TransactionDTO> createTransaction(
            @PathVariable String accountId,
            @RequestBody TransactionDTO transactionRequest) {

        Optional<BankAccount> bankAccount = bankAccountService.getBankAccount(accountId);
        Transaction transaction = transactionsService.createTransaction(Mapper.dtoToTransaction(transactionRequest, bankAccount));

        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.transactionToDto(transaction));
    }

}
