package ec.com.example.bank_account.repository;

import ec.com.example.bank_account.entity.TypeAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeAccountRepository extends MongoRepository<TypeAccount, String> {

    TypeAccount save(TypeAccount typeAccount);

    List<TypeAccount> findAll();

}