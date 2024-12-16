package ec.com.example.bank_account.repository;

import ec.com.example.bank_account.entity.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    Transaction save(Transaction transaction);

    List<Transaction> findAll();

}
