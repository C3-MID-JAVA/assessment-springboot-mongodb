package org.example.financespro.repository;

import java.util.Optional;
import org.example.financespro.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
  Optional<Account> findByAccountNumber(String accountNumber);
}
