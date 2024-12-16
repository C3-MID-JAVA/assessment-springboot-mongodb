package org.bankAccountManager.service.implementations;

import org.bankAccountManager.entity.Card;
import org.bankAccountManager.repository.CardRepository;
import org.bankAccountManager.service.interfaces.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImplementation implements CardService {

    private final CardRepository cardRepository;

    public CardServiceImplementation(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card createCard(Card card) {
        if (cardRepository.existsById(card.getId()))
            throw new IllegalArgumentException("Card already exists");
        return cardRepository.save(card);
    }

    @Override
    public Card getCardById(int id) {
        return cardRepository.findCardById(id);
    }

    @Override
    public Card getCardByNumber(String card_number) {
        return cardRepository.findCardByCardNumber(card_number);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public List<Card> getCardsByAccountId(int account_id) {
        return cardRepository.findCardsByAccountId(account_id);
    }

    @Override
    public List<Card> getCardsByType(String card_type) {
        return cardRepository.findCardsByCardType(card_type);
    }

    @Override
    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public void deleteCard(Card card) {
        cardRepository.delete(card);
    }
}
