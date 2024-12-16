package org.bankAccountManager.mapper;

import org.bankAccountManager.DTO.response.*;
import org.bankAccountManager.entity.*;

public class DTOResponseMapper {
    public static AccountResponseDTO toAccountResponseDTO(Account a) {
        return new AccountResponseDTO(
                a.getAccountNumber(),
                a.getAccountType(),
                a.getBalance(),
                toCustomerResponseDTO(a.getCustomer()),
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
                toAccountResponseDTO(c.getAccount()),
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
                c.getPhone());
    }

    public static TransactionResponseDTO toTransactionResponseDTO(Transaction t) {
        return new TransactionResponseDTO(
                t.getAmount(),
                toBranchResponseDTO(t.getBranch()),
                t.getDate(),
                t.getDescription(),
                toAccountResponseDTO(t.getDestinationAccount()),
                t.getId(),
                toAccountResponseDTO(t.getSourceAccount()),
                t.getType());
    }

    public static Account toAccount(AccountResponseDTO aDTO) {
        return new Account(
                aDTO.getAccount_number(),
                aDTO.getAccount_type(),
                aDTO.getBalance(),
                toCustomer(aDTO.getCustomer()),
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
                toAccount(cDTO.getAccount()),
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
                cDTO.getPhone());
    }

    public static Transaction toTransaction(TransactionResponseDTO tDTO) {
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
