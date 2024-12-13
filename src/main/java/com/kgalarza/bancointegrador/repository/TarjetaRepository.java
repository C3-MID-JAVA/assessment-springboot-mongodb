package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {
}
