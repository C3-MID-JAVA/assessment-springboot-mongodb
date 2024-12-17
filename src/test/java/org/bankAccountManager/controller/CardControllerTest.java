package org.bankAccountManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bankAccountManager.DTO.request.CardRequestDTO;
import org.bankAccountManager.DTO.response.CardResponseDTO;
import org.bankAccountManager.entity.Card;
import org.bankAccountManager.service.implementations.CardServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.bankAccountManager.mapper.DTOResponseMapper.toCard;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CardController.class)
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardServiceImplementation cardService;

    @Autowired
    private ObjectMapper objectMapper;

    private CardRequestDTO cardRequestDTO;
    private CardResponseDTO cardResponseDTO;

    @BeforeEach
    void setUp() {
        cardRequestDTO = new CardRequestDTO();
        cardRequestDTO.setId(1);
        cardRequestDTO.setCard_number("1234-5678-9012-3456");
        cardRequestDTO.setCard_type("Credit");

        cardResponseDTO = new CardResponseDTO();
        cardResponseDTO.setId(1);
        cardResponseDTO.setCard_number("1234-5678-9012-3456");
        cardResponseDTO.setCard_type("Credit");
    }

    @Test
    void createCard_ShouldReturn201() throws Exception {
        Mockito.when(cardService.createCard(any())).thenReturn(toCard(cardResponseDTO));

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.card_number").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$.card_type").value("Credit"));
    }

    @Test
    void getCardById_ShouldReturnCard() throws Exception {
        Mockito.when(cardService.getCardById(1)).thenReturn(toCard(cardResponseDTO));

        mockMvc.perform(get("/cards/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.card_number").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$.card_type").value("Credit"));
    }

    @Test
    void getAllCards_ShouldReturnListOfCards() throws Exception {
        List<Card> cardList = List.of(toCard(cardResponseDTO));
        Mockito.when(cardService.getAllCards()).thenReturn(cardList);
        mockMvc.perform(get("/cards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].card_number").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$[0].card_type").value("Credit"));
    }

    @Test
    void updateCard_ShouldReturnUpdatedCard() throws Exception {
        Mockito.when(cardService.updateCard(any())).thenReturn(toCard(cardResponseDTO));

        mockMvc.perform(put("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.card_number").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$.card_type").value("Credit"));
    }

    @Test
    void deleteCard_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(cardService).deleteCard(any());
        mockMvc.perform(delete("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cardRequestDTO)))
                .andExpect(status().isNoContent());
    }
}
