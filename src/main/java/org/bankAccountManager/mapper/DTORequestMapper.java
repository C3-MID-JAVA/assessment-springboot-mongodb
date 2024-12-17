package org.bankAccountManager.mapper;

import org.bankAccountManager.DTO.request.*;
import org.bankAccountManager.entity.*;

public class DTORequestMapper {
    public static AccountRequestDTO toAccountRequestDTO(Account a) {
        return new AccountRequestDTO(
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance(),
                a.getCards().stream().map(DTORequestMapper::toCardRequestDTO).toList(),
                a.getTransactions().stream().map(DTORequestMapper::toTransactionRequestDTO).toList(),
                a.getId());
    }

    public static BranchRequestDTO toBranchRequestDTO(Branch b) {
        return new BranchRequestDTO(
                b.getAddress(),
                b.getId(),
                b.getName(),
                b.getPhone());
    }

    public static CardRequestDTO toCardRequestDTO(Card c) {
        return new CardRequestDTO(
                c.getCardNumber(),
                c.getCardType(),
                c.getCvv(),
                c.getExpirationDate(),
                c.getId());
    }

    public static CustomerRequestDTO toCustomerRequestDTO(Customer c) {
        return new CustomerRequestDTO(
                c.getAddress(),
                c.getEmail(),
                c.getFirstName(),
                c.getId(),
                c.getLastName(),
                c.getPhone(),
                c.getAccounts().stream().map(DTORequestMapper::toAccountRequestDTO).toList());
    }

    public static TransactionRequestDTO toTransactionRequestDTO(Transaction t) {
        return new TransactionRequestDTO(
                t.getAmount(),
                t.getBranches().stream().map(DTORequestMapper::toBranchRequestDTO).toList(),
                t.getDate(),
                t.getDescription(),
                t.getId(),
                t.getType());
    }

    public static Account toAccount(AccountRequestDTO aDTO) {
        return new Account(
                aDTO.getAccount_number(),
                aDTO.getAccount_type(),
                aDTO.getBalance(),
                aDTO.getCards().stream().map(DTORequestMapper::toCard).toList(),
                aDTO.getTransactions().stream().map(DTORequestMapper::toTransaction).toList(),
                aDTO.getId());
    }

    public static Branch toBranch(BranchRequestDTO bDTO) {
        return new Branch(
                bDTO.getAddress(),
                bDTO.getId(),
                bDTO.getName(),
                bDTO.getPhone());
    }

    public static Card toCard(CardRequestDTO cDTO) {
        return new Card(
                cDTO.getCard_number(),
                cDTO.getCard_type(),
                cDTO.getExpiration_date(),
                cDTO.getId());
    }

    public static Customer toCustomer(CustomerRequestDTO cDTO) {
        return new Customer(
                cDTO.getAddress(),
                cDTO.getEmail(),
                cDTO.getFirst_name(),
                cDTO.getId(),
                cDTO.getLast_name(),
                cDTO.getPhone(),
                cDTO.getAccounts().stream().map(DTORequestMapper::toAccount).toList());
    }

    public static Transaction toTransaction(TransactionRequestDTO tDTO) {
        return new Transaction(
                tDTO.getAmount(),
                tDTO.getBranches().stream().map(DTORequestMapper::toBranch).toList(),
                tDTO.getDate(),
                tDTO.getDescription(),
                tDTO.getId(),
                tDTO.getType());
    }
}
