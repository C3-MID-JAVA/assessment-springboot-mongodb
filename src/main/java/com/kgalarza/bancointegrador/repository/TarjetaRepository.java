package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Tarjeta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends MongoRepository<Tarjeta, Long> {
}
