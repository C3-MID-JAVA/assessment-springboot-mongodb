package org.bankAccountManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bankAccountManager.DTO.request.BranchRequestDTO;
import org.bankAccountManager.DTO.response.BranchResponseDTO;
import org.bankAccountManager.service.implementations.BranchServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.bankAccountManager.mapper.DTORequestMapper.*;
import static org.bankAccountManager.mapper.DTOResponseMapper.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BranchController.class)
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
public class BranchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BranchServiceImplementation branchService;

    @Autowired
    private ObjectMapper objectMapper;

    private BranchRequestDTO branchRequestDTO;
    private BranchResponseDTO branchResponseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear datos de prueba
        branchRequestDTO = new BranchRequestDTO(1, "Main Branch", "123 Main St.");
        branchResponseDTO = new BranchResponseDTO(1, "Main Branch", "123 Main St.");
    }

    @Test
    void createBranch() throws Exception {
        when(branchService.createBranch(toBranch(branchRequestDTO)))
                .thenReturn(toBranch(branchResponseDTO));
        mockMvc.perform(post("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main Branch"))
                .andExpect(jsonPath("$.address").value("123 Main St."));
    }

    @Test
    void getBranchById() throws Exception {
        when(branchService.getBranchById(1)).thenReturn(toBranch(branchRequestDTO));
        mockMvc.perform(get("/branches/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main Branch"))
                .andExpect(jsonPath("$.address").value("123 Main St."));
    }

    @Test
    void getBranchByName() throws Exception {
        when(branchService.getBranchByName("Main Branch")).thenReturn(toBranch(branchRequestDTO));
        mockMvc.perform(get("/branches/name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main Branch"))
                .andExpect(jsonPath("$.address").value("123 Main St."));
    }

    @Test
    void updateBranch() throws Exception {
        when(branchService.updateBranch(toBranch(branchRequestDTO)))
                .thenReturn(toBranch(branchResponseDTO));
        mockMvc.perform(put("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Main Branch"))
                .andExpect(jsonPath("$.address").value("123 Main St."));
    }

    @Test
    void deleteBranch() throws Exception {
        when(branchService.getBranchById(1)).thenReturn(toBranch(branchRequestDTO));
        mockMvc.perform(delete("/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(branchRequestDTO)))
                .andExpect(status().isNoContent());
    }
}
