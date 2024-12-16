package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountById(int id);

    Boolean existsById(int id);

    Account findAccountByCustomerId(int customer_id);

    List<Account> findAll();
}
