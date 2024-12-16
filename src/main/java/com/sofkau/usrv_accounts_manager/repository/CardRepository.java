package com.sofkau.usrv_accounts_manager.repository;


import com.sofkau.usrv_accounts_manager.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CardRepository extends MongoRepository<CardModel, String> {
    boolean existsByCardCVV(String cardCVV);
    Optional<CardModel> findByCardNumber(String cardNumber);
}
