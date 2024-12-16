package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerById(int id);

    Boolean existsById(int id);

    Customer findCustomerByFirstName(String first_name);

    Customer findCustomerByLastName(String last_name);

    Customer findCustomerByEmail(String email);

    List<Customer> findAll();
}
