package org.bankAccountManager.service.interfaces;

import org.bankAccountManager.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer);

    Customer getCustomerById(int id);

    Customer getCustomerByFirstName(String first_name);

    Customer getCustomerByLastName(String last_name);

    Customer getCustomerByEmail(String email);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Customer customer);

    void deleteCustomer(Customer customer);
}