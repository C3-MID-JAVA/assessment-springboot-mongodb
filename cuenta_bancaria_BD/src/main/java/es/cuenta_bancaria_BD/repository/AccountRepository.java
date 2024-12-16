package es.cuenta_bancaria_BD.repository;


import es.cuenta_bancaria_BD.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account,String> {
}
