package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.TransactionRequestDTO;
import ec.com.example.bank_account.dto.TransactionResponseDTO;
import ec.com.example.bank_account.entity.Transaction;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.exception.TransactionRejectedException;
import ec.com.example.bank_account.mapper.TransactionMapper;
import ec.com.example.bank_account.repository.AccountRepository;
import ec.com.example.bank_account.repository.TransactionRepository;
import ec.com.example.bank_account.service.TransactionService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionResponseDTO createTransaction(TransactionRequestDTO transactionRequestDTO) {
        Transaction transaction = transactionMapper.mapToEntity(transactionRequestDTO);

        if (transaction.getAccount() == null || !"ACTIVE".equals(transaction.getAccount().getStatus())) {
            throw new TransactionRejectedException("Inactive or invalid account.");
        }

        if (!transaction.getTypeTransaction().getTransactionCost() &&
                transaction.getValue().compareTo(transaction.getTypeTransaction().getValue()) > 0) {
            throw new TransactionRejectedException("The transaction cost is higher than the amount to be sent.");
        }

        BigDecimal balance = transaction.getAccount().getAvailableBalance()
                .subtract(transaction.getTypeTransaction().getValue());

        if (transaction.getTypeTransaction().getDiscount()) {
            if (balance.compareTo(transaction.getValue()) < 0) {
                throw new TransactionRejectedException("the account does not have sufficient funds.");
            }
            balance = balance.subtract(transaction.getValue());
        } else {
            balance = balance.add(transaction.getValue());
        }

        transaction.getAccount().setAvailableBalance(balance);
        accountRepository.save(transaction.getAccount());
        return transactionMapper.mapToDTO(transactionRepository.save(transaction));

    }

    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        List<TransactionResponseDTO> response = transactionRepository.findAll().stream()
                .map(transactionMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No transactions records found.");
        }

        return response;
    }

}