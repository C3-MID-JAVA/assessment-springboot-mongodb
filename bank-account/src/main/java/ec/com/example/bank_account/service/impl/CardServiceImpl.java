package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.CardRequestDTO;
import ec.com.example.bank_account.dto.CardResponseDTO;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.CardMapper;
import ec.com.example.bank_account.repository.CardRepository;
import ec.com.example.bank_account.service.CardService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardServiceImpl(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardResponseDTO createCard(CardRequestDTO card) {
        return cardMapper.mapToDTO(cardRepository.save(cardMapper.mapToEntity(card)));
    }

    @Override
    public List<CardResponseDTO> getAllCards() {
        List<CardResponseDTO> response = cardRepository.findAll().stream()
                .map(cardMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No cards records found.");
        }
        return response;
    }
}