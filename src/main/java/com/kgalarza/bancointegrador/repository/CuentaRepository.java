package com.kgalarza.bancointegrador.repository;

import com.kgalarza.bancointegrador.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
}
