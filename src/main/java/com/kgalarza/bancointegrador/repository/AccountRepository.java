package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
}
