package org.bankAccountManager.service.interfaces;

import org.bankAccountManager.entity.Card;

import java.util.List;

public interface CardService {
    Card createCard(Card card);

    Card getCardById(int id);

    Card getCardByNumber(String card_number);

    List<Card> getAllCards();

    List<Card> getCardsByAccountId(int account_id);

    List<Card> getCardsByType(String card_type);

    Card updateCard(Card card);

    void deleteCard(Card card);
}