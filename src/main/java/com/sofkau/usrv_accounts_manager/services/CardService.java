package com.sofkau.usrv_accounts_manager.services;

import com.sofkau.usrv_accounts_manager.dto.CardDTO;
import com.sofkau.usrv_accounts_manager.model.CardModel;

public interface CardService {
    boolean existsCvv(String cvv);
    CardDTO createCard(CardDTO cardDTO);
}
