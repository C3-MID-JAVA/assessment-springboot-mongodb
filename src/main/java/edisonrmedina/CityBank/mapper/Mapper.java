package edisonrmedina.CityBank.mapper;

import edisonrmedina.CityBank.dto.BankAccountDTO;
import edisonrmedina.CityBank.dto.TransactionDTO;
import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.entity.transaction.Transaction;
import edisonrmedina.CityBank.entity.transaction.TransactionCostStrategy.TransactionCostStrategy;
import edisonrmedina.CityBank.enums.TransactionType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Mapper {

    private final Map<TransactionType, TransactionCostStrategy> strategies = new HashMap<>();

    public static BankAccountDTO bankAccountToDTO(BankAccount bankAccount) {
        return BankAccountDTO
                .builder()
                .id(bankAccount.getId())
                .accountHolder(bankAccount.getAccountHolder())
                .balance(bankAccount.getBalance())
                .build();
    }

    public static BankAccount dtoToBankAccount(BankAccountDTO bankAccountDTO) {
        return new BankAccount(bankAccountDTO.getAccountHolder(),bankAccountDTO.getBalance());
    }

    public static TransactionDTO transactionToDto(Transaction transaction) {
        return new TransactionDTO(
                transaction.getType(),
                transaction.getAmount(),
                transaction.getBankAccount().getId(),
                transaction.getTransactionCost()
        );
    }

    public static Transaction dtoToTransaction(TransactionDTO transactionDTO, Optional<BankAccount> bankAccount) {
        return new Transaction(
                transactionDTO.getType(),
                transactionDTO.getAmount(),
                transactionDTO.getTransactionCost(),
                bankAccount.get()
        );
    }


}
