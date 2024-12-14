package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {
}
