package ec.com.example.bank_account.repository;

import ec.com.example.bank_account.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {

    Card save(Card card);

}