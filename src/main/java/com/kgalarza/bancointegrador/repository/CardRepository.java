package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Card;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends MongoRepository<Card, String> {
}
