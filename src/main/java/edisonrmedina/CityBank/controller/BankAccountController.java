package edisonrmedina.CityBank.controller;

import edisonrmedina.CityBank.dto.BankAccountDTO;
import edisonrmedina.CityBank.dto.CreateBankAccountDTO;
import edisonrmedina.CityBank.mapper.Mapper;
import edisonrmedina.CityBank.service.BankAccountService;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank-account")
public class BankAccountController {

    private final BankAccountServiceImp bankAccountServiceImp;

    @Autowired
    public BankAccountController(BankAccountServiceImp bankAccountServiceImp) {
        this.bankAccountServiceImp = bankAccountServiceImp;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDTO> getBankAccount(@PathVariable String id) {
        return bankAccountServiceImp.getBankAccount(id)
                .map(account -> ResponseEntity.ok(Mapper.bankAccountToDTO(account)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createBankAccount(@Valid @RequestBody CreateBankAccountDTO createBankAccountDTO) {
        var bankAccountDTO = BankAccountDTO.builder().accountHolder(createBankAccountDTO.getAccountHolder()).balance(createBankAccountDTO.getBalance()).build();
        var createdBankAccount = bankAccountServiceImp.register(Mapper.dtoToBankAccount(bankAccountDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.bankAccountToDTO(createdBankAccount));
    }


}
