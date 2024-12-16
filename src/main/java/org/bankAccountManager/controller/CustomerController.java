package org.bankAccountManager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bankAccountManager.DTO.request.CustomerRequestDTO;
import org.bankAccountManager.DTO.response.CustomerResponseDTO;
import org.bankAccountManager.mapper.DTOResponseMapper;
import org.bankAccountManager.service.implementations.CustomerServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bankAccountManager.mapper.DTORequestMapper.toCustomer;
import static org.bankAccountManager.mapper.DTOResponseMapper.toCustomerResponseDTO;

@Tag(name = "Customer Management", description = "Endpoints for managing customers")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerServiceImplementation customerService;

    public CustomerController(CustomerServiceImplementation customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Create a new customer", description = "Add a new customer to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toCustomerResponseDTO(customerService.createCustomer(toCustomer(customer))));
    }

    @Operation(summary = "Retrieve a customer by ID", description = "Get details of a customer by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/id")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.ok(toCustomerResponseDTO(customerService.getCustomerById(customer.getId())));
    }

    @Operation(summary = "Retrieve a customer by first name", description = "Get details of a customer by their first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/firstName")
    public ResponseEntity<CustomerResponseDTO> getCustomerByFirstName(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.ok(toCustomerResponseDTO(customerService.getCustomerByFirstName(customer.getFirst_name())));
    }

    @Operation(summary = "Retrieve a customer by last name", description = "Get details of a customer by their last name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/lastName")
    public ResponseEntity<CustomerResponseDTO> getCustomerByLastName(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.ok(toCustomerResponseDTO(customerService.getCustomerByFirstName(customer.getLast_name())));
    }

    @Operation(summary = "Retrieve a customer by email", description = "Get details of a customer by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/email")
    public ResponseEntity<CustomerResponseDTO> getCustomerByEmail(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.ok(toCustomerResponseDTO(customerService.getCustomerByEmail(customer.getEmail())));
    }

    @Operation(summary = "Retrieve all customers", description = "Get a list of all customers in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers().stream().map(DTOResponseMapper::toCustomerResponseDTO).toList());
    }

    @Operation(summary = "Update a customer", description = "Update details of an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @PutMapping
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerRequestDTO customer) {
        return ResponseEntity.ok(toCustomerResponseDTO(customerService.updateCustomer(toCustomer(customer))));
    }

    @Operation(summary = "Delete a customer", description = "Remove a customer from the system by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping
    public ResponseEntity<Void> deleteCustomer(@RequestBody CustomerRequestDTO customer) {
        customerService.deleteCustomer(customerService.getCustomerById(customer.getId()));
        return ResponseEntity.noContent().build();
    }
}
