package ec.com.example.bank_account.repository;

import ec.com.example.bank_account.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

    Account save(Account account);

    List<Account> findAll();

    Account findByNumber(String number);

}
