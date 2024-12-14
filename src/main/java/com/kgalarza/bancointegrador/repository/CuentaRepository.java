package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, Long> {
}
