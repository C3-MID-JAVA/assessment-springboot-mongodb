package co.com.sofka.cuentabancaria.repository;

import co.com.sofka.cuentabancaria.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {

    List<Transaccion> findByCuentaId(Long cuentaId);
}
