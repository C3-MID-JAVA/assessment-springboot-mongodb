package org.bankAccountManager.service.implementations;

import org.bankAccountManager.entity.Customer;
import org.bankAccountManager.repository.CustomerRepository;
import org.bankAccountManager.service.interfaces.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImplementation(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsById(customer.getId()))
            throw new IllegalArgumentException("Customer already exists");
        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(int id) {
        return customerRepository.findCustomerById(id);
    }

    @Override
    public Customer getCustomerByFirstName(String first_name) {
        return customerRepository.findCustomerByFirstName(first_name);
    }

    @Override
    public Customer getCustomerByLastName(String last_name) {
        return customerRepository.findCustomerByFirstName(last_name);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }
}
