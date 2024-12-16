package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Card findCardById(int id);

    Boolean existsById(int id);

    Card findCardByCardNumber(String card_number);

    List<Card> findAll();

    List<Card> findCardsByAccountId(int account_id);

    List<Card> findCardsByCardType(String card_type);
}
