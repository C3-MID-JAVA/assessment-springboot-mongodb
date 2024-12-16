package ec.com.example.bank_account.repository;

import ec.com.example.bank_account.entity.TypeTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeTransactionRepository extends MongoRepository<TypeTransaction, String> {

    TypeTransaction save(TypeTransaction typeTransaction);

    List<TypeTransaction> findAll();

}