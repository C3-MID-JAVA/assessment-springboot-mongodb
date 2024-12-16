package com.sofkau.usrv_accounts_manager.services.impl;

import com.sofkau.usrv_accounts_manager.dto.TransactionDTO;
import com.sofkau.usrv_accounts_manager.mapper.DTOMapper;
import com.sofkau.usrv_accounts_manager.model.AccountModel;
import com.sofkau.usrv_accounts_manager.model.CardModel;
import com.sofkau.usrv_accounts_manager.model.abstracts.TransactionModel;
import com.sofkau.usrv_accounts_manager.model.classes.AccountDeposit;
import com.sofkau.usrv_accounts_manager.repository.AccountRepository;
import com.sofkau.usrv_accounts_manager.repository.CardRepository;
import com.sofkau.usrv_accounts_manager.repository.TransactionRepository;
import com.sofkau.usrv_accounts_manager.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository, CardRepository cardRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        AccountModel accountReceiver;
        AccountModel account;
        CardModel card = null;
        TransactionModel transactionModel = DTOMapper.toTransactionModel(transactionDTO);

        if (transactionModel instanceof AccountDeposit) {
            account = accountRepository
                    .findByAccountNumber(transactionDTO.getAccount().getAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Sending account not found"));
            accountReceiver = accountRepository
                    .findByAccountNumber(transactionDTO.getAccountReceiver().getAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Receiving account not found"));
            transactionModel.processTransaction();

            accountReceiver.setBalance(account.getBalance().add(transactionModel.getAmount()));
            account.setBalance(account.getBalance().subtract(transactionModel.getAmount()).subtract(transactionModel.getTransactionFee()));

            ((AccountDeposit) transactionModel).setAccountReceiver(accountReceiver);
            accountRepository.save(accountReceiver);


        } else {
            card = cardRepository
                    .findByCardNumber(transactionDTO.getCard().getCardNumber())
                    .orElseThrow(() -> new RuntimeException("Card not found"));
            account = accountRepository
                    .findByAccountNumber(transactionDTO.getAccount().getAccountNumber())
                    .orElseThrow(() -> new RuntimeException("Sending account not found"));

            transactionModel.processTransaction();
            account.setBalance(account.getBalance().subtract(transactionModel.getAmount()).subtract(transactionModel.getTransactionFee()));
        }



        transactionModel.setTimestamp(LocalDateTime.now());
        transactionModel.setAccount(account);
        transactionModel.setCard(card);
        accountRepository.save(account);

        TransactionModel savedTransaction = transactionRepository.save(transactionModel);

        return DTOMapper.toTransactionDTO(savedTransaction);
    }


}
