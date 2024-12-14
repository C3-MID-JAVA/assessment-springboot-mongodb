package co.com.sofka.cuentabancaria.repository;

import co.com.sofka.cuentabancaria.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {

    List<Transaccion> findByCuentaId(String cuentaId);
}
