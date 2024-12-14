package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Movimiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends MongoRepository<Movimiento, Long> {
}
