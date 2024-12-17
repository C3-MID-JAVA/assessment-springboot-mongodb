package org.bankAccountManager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bankAccountManager.DTO.request.CustomerRequestDTO;
import org.bankAccountManager.DTO.response.CustomerResponseDTO;
import org.bankAccountManager.entity.Customer;
import org.bankAccountManager.service.implementations.CustomerServiceImplementation;
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

import static org.bankAccountManager.mapper.DTOResponseMapper.toCustomer;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImplementation customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerRequestDTO customerRequestDTO;
    private CustomerResponseDTO customerResponseDTO;

    @BeforeEach
    void setUp() {
        customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setId(1);
        customerRequestDTO.setFirst_name("John");
        customerRequestDTO.setLast_name("Doe");
        customerRequestDTO.setEmail("john.doe@example.com");

        customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setId(1);
        customerResponseDTO.setFirst_name("John");
        customerResponseDTO.setLast_name("Doe");
        customerResponseDTO.setEmail("john.doe@example.com");
    }

    @Test
    void createCustomer_ShouldReturn201() throws Exception {
        Mockito.when(customerService.createCustomer(any())).thenReturn(toCustomer(customerResponseDTO));

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.last_name").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() throws Exception {
        Mockito.when(customerService.getCustomerById(1)).thenReturn(toCustomer(customerResponseDTO));

        mockMvc.perform(get("/customers/id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.last_name").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void getAllCustomers_ShouldReturnListOfCustomers() throws Exception {
        List<Customer> customerList = List.of(toCustomer(customerResponseDTO));
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerList);
        mockMvc.perform(get("/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].first_name").value("John"))
                .andExpect(jsonPath("$[0].last_name").value("Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        Mockito.when(customerService.updateCustomer(any())).thenReturn(toCustomer(customerResponseDTO));
        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.first_name").value("John"))
                .andExpect(jsonPath("$.last_name").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void deleteCustomer_ShouldReturn204() throws Exception {
        Mockito.doNothing().when(customerService).deleteCustomer(any());
        mockMvc.perform(delete("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequestDTO)))
                .andExpect(status().isNoContent());
    }
}
