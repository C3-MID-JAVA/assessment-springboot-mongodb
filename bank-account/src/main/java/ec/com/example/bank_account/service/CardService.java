package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.CardRequestDTO;
import ec.com.example.bank_account.dto.CardResponseDTO;

import java.util.List;

public interface CardService {

    CardResponseDTO createCard(CardRequestDTO card);

    List<CardResponseDTO> getAllCards();

}