package com.sofkau.usrv_accounts_manager.repository;

import com.sofkau.usrv_accounts_manager.model.AccountModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("Should found an account on the database with the same account number")
    void findByAccountNumber() {
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountNumber("123456789");
        AccountModel accountModelSaved = accountRepository.save(accountModel);
        Optional<AccountModel> accountModelResponse = accountRepository.findByAccountNumber("123456789");
        assertTrue(accountModelResponse.isPresent());
        assertEquals(accountModelSaved.getAccountNumber(), accountModelResponse.get().getAccountNumber());
    }

    @Test
    @DisplayName("Should NOT found an account the database with the same account number WHEN account not exist")
    void findByAccountNumber_Notfound() {
        AccountModel accountModel = new AccountModel();
        accountModel.setAccountNumber("123456789");
        AccountModel accountModelSaved = accountRepository.save(accountModel);
        Optional<AccountModel> accountModelResponse = accountRepository.findByAccountNumber("123456789");
        assertTrue(accountModelResponse.isPresent());
        assertEquals(accountModelSaved.getAccountNumber(), accountModelResponse.get().getAccountNumber());
    }
}