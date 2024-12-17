package org.bankAccountManager.mapper;

import org.bankAccountManager.DTO.response.*;
import org.bankAccountManager.entity.*;

public class DTOResponseMapper {
    public static AccountResponseDTO toAccountResponseDTO(Account a) {
        return new AccountResponseDTO(
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance(),
                a.getCards().stream().map(DTOResponseMapper::toCardResponseDTO).toList(),
                a.getTransactions().stream().map(DTOResponseMapper::toTransactionResponseDTO).toList(),
                a.getId());
    }

    public static BranchResponseDTO toBranchResponseDTO(Branch b) {
        return new BranchResponseDTO(
                b.getAddress(),
                b.getId(),
                b.getName(),
                b.getPhone());
    }

    public static CardResponseDTO toCardResponseDTO(Card c) {
        return new CardResponseDTO(
                c.getCardNumber(),
                c.getCardType(),
                c.getCvv(),
                c.getExpirationDate(),
                c.getId());
    }

    public static CustomerResponseDTO toCustomerResponseDTO(Customer c) {
        return new CustomerResponseDTO(
                c.getAddress(),
                c.getEmail(),
                c.getFirstName(),
                c.getId(),
                c.getLastName(),
                c.getPhone(),
                c.getAccounts().stream().map(DTOResponseMapper::toAccountResponseDTO).toList());
    }

    public static TransactionResponseDTO toTransactionResponseDTO(Transaction t) {
        return new TransactionResponseDTO(
                t.getAmount(),
                t.getBranches().stream().map(DTOResponseMapper::toBranchResponseDTO).toList(),
                t.getDate(),
                t.getDescription(),
                t.getId(),
                t.getType());
    }

    public static Account toAccount(AccountResponseDTO aDTO) {
        return new Account(
                aDTO.getAccount_number(),
                aDTO.getAccount_type(),
                aDTO.getBalance(),
                aDTO.getCards().stream().map(DTOResponseMapper::toCard).toList(),
                aDTO.getTransactions().stream().map(DTOResponseMapper::toTransaction).toList(),
                aDTO.getId());
    }

    public static Branch toBranch(BranchResponseDTO bDTO) {
        return new Branch(
                bDTO.getAddress(),
                bDTO.getId(),
                bDTO.getName(),
                bDTO.getPhone());
    }

    public static Card toCard(CardResponseDTO cDTO) {
        return new Card(
                cDTO.getCard_number(),
                cDTO.getCard_type(),
                cDTO.getExpiration_date(),
                cDTO.getId());
    }

    public static Customer toCustomer(CustomerResponseDTO cDTO) {
        return new Customer(
                cDTO.getAddress(),
                cDTO.getEmail(),
                cDTO.getFirst_name(),
                cDTO.getId(),
                cDTO.getLast_name(),
                cDTO.getPhone(),
                cDTO.getAccounts().stream().map(DTOResponseMapper::toAccount).toList());
    }

    public static Transaction toTransaction(TransactionResponseDTO tDTO) {
        return new Transaction(
                tDTO.getAmount(),
                tDTO.getBranches().stream().map(DTOResponseMapper::toBranch).toList(),
                tDTO.getDate(),
                tDTO.getDescription(),
                tDTO.getId(),
                tDTO.getType());
    }
}
