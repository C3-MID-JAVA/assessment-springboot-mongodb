package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
}
