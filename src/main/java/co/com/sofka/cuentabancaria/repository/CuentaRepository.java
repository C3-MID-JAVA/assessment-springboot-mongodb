package co.com.sofka.cuentabancaria.repository;

import co.com.sofka.cuentabancaria.model.Cuenta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CuentaRepository  extends MongoRepository<Cuenta, String> {
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
