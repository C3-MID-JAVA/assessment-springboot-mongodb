package ec.com.example.bank_account.mapper;

import ec.com.example.bank_account.dto.TransactionRequestDTO;
import ec.com.example.bank_account.dto.TransactionResponseDTO;
import ec.com.example.bank_account.entity.Account;
import ec.com.example.bank_account.entity.Transaction;
import ec.com.example.bank_account.entity.TypeTransaction;
import ec.com.example.bank_account.exception.RecordNotFoundException;
import ec.com.example.bank_account.repository.AccountRepository;
import ec.com.example.bank_account.repository.TypeTransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    private final AccountRepository accountRepository;
    private final TypeTransactionRepository typeTransactionRepository;
    private final AccountMapper accountMapper;
    private final TypeTransactionMapper typeTransactionMapper;

    public TransactionMapper(AccountMapper accountMapper, AccountRepository accountRepository,
                             TypeTransactionRepository typeTransactionRepository,
                             TypeTransactionMapper typeTransactionMapper) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.typeTransactionRepository = typeTransactionRepository;
        this.typeTransactionMapper = typeTransactionMapper;
    }

    public Transaction mapToEntity(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO == null) {
            return null;
        }

        Account account = accountRepository.findByNumber(transactionRequestDTO.getAccountNumber());

        TypeTransaction typeTransaction = typeTransactionRepository.findById(transactionRequestDTO.getTypeTransactionId())
                .orElseThrow(() -> new RecordNotFoundException("TypeTransaction not found."));

        Transaction transaction = new Transaction();
        transaction.setDetails(transactionRequestDTO.getDetails());
        transaction.setDate(transactionRequestDTO.getDate());
        transaction.setValue(transactionRequestDTO.getValue());
        transaction.setStatus(transactionRequestDTO.getStatus());
        transaction.setAccount(account);
        transaction.setTypeTransaction(typeTransaction);

        return transaction;

    }

    public TransactionResponseDTO mapToDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        return new TransactionResponseDTO(
                transaction.getValue(),
                transaction.getDate(),
                transaction.getStatus(),
                accountMapper.mapToDTO(transaction.getAccount()),
                typeTransactionMapper.mapToDTO(transaction.getTypeTransaction())
        );
    }
}