package com.bankmanagement.bankmanagement.controller;

import com.bankmanagement.bankmanagement.dto.UserRequestDTO;
import com.bankmanagement.bankmanagement.dto.UserResponseDTO;
import com.bankmanagement.bankmanagement.exception.BadRequestException;
import com.bankmanagement.bankmanagement.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        validUserRequest = new UserRequestDTO();
        validUserRequest.setDocumentId("12345678");
        validUserRequest.setName("John Doe");

        userResponse = new UserResponseDTO();
        userResponse.setId("1");
        userResponse.setDocumentId("12345678");
        userResponse.setName("John Doe");
    }

    private UserRequestDTO validUserRequest;
    private UserResponseDTO userResponse;

    @Test
    void register_ValidUser_ReturnsCreatedResponse() throws Exception {
        when(userService.register(any(UserRequestDTO.class))).thenReturn(userResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.documentId").value("12345678"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(userService, times(1)).register(any(UserRequestDTO.class));
    }

    @Test
    void register_DuplicateUser_ReturnsBadRequest() throws Exception {
        when(userService.register(any(UserRequestDTO.class)))
                .thenThrow(new BadRequestException("Document ID already exists."));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUserRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Document ID already exists."));

        verify(userService, times(1)).register(any(UserRequestDTO.class));
    }

    @Test
    void register_EmptyDocumentId_ReturnsBadRequest() throws Exception {
        UserRequestDTO invalidUserRequest = new UserRequestDTO();
        invalidUserRequest.setDocumentId("");
        invalidUserRequest.setName("John Doe");

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserRequest)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).register(any(UserRequestDTO.class));
    }
}