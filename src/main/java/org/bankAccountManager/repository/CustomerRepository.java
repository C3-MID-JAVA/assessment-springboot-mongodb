package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {
    Customer findCustomerById(int id);

    Boolean existsById(int id);

    Customer findCustomerByFirstName(String first_name);

    Customer findCustomerByLastName(String last_name);

    Customer findCustomerByEmail(String email);

    List<Customer> findAll();
}
