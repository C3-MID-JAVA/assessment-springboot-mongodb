package org.bankAccountManager.mapper;

import org.bankAccountManager.DTO.request.*;
import org.bankAccountManager.entity.*;

public class DTORequestMapper {
    public static AccountRequestDTO toAccountRequestDTO(Account a) {
        return new AccountRequestDTO(
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance(),
                toCustomerRequestDTO(a.getCustomer()),
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
                toAccountRequestDTO(c.getAccount()),
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
                c.getPhone());
    }

    public static TransactionRequestDTO toTransactionRequestDTO(Transaction t) {
        return new TransactionRequestDTO(
                t.getAmount(),
                toBranchRequestDTO(t.getBranch()),
                t.getDate(),
                t.getDescription(),
                toAccountRequestDTO(t.getDestinationAccount()),
                t.getId(),
                toAccountRequestDTO(t.getSourceAccount()),
                t.getType());
    }

    public static Account toAccount(AccountRequestDTO aDTO) {
        return new Account(
                aDTO.getAccount_number(),
                aDTO.getAccount_type(),
                aDTO.getBalance(),
                toCustomer(aDTO.getCustomer()),
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
                toAccount(cDTO.getAccount()),
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
                cDTO.getPhone());
    }

    public static Transaction toTransaction(TransactionRequestDTO tDTO) {
        return new Transaction(
                tDTO.getAmount(),
                toBranch(tDTO.getBranch()),
                tDTO.getDate(),
                tDTO.getDescription(),
                toAccount(tDTO.getDestination_account()),
                tDTO.getId(),
                toAccount(tDTO.getSource_account()),
                tDTO.getType());
    }
}
