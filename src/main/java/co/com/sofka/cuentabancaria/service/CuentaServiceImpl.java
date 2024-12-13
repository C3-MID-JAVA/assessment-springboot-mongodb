package co.com.sofka.cuentabancaria.service;

import co.com.sofka.cuentabancaria.dto.cuenta.CuentaRequestDTO;
import co.com.sofka.cuentabancaria.dto.cuenta.CuentaResponseDTO;
import co.com.sofka.cuentabancaria.model.Cuenta;
import co.com.sofka.cuentabancaria.repository.CuentaRepository;
import co.com.sofka.cuentabancaria.service.iservice.CuentaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl  implements CuentaService {

    private final CuentaRepository cuentaRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    @Override
    public List<CuentaResponseDTO> obtenerCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();

        List<CuentaResponseDTO> cuentasDTO = cuentas.stream().map(c -> new CuentaResponseDTO(c)).collect(Collectors.toList());
        return cuentasDTO;
    }

    @Override
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO cuentaRequestDTO) {
        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setNumeroCuenta(cuentaRequestDTO.getNumeroCuenta());
        nuevaCuenta.setSaldo(cuentaRequestDTO.getSaldoInicial());
        nuevaCuenta.setTitular(cuentaRequestDTO.getTitular());

        Cuenta cuenta = cuentaRepository.save(nuevaCuenta);

        return new CuentaResponseDTO(cuenta);
    }

    @Override
    public CuentaResponseDTO obtenerCuentaPorId(Long id) {

        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro el cuenta con id: " + id)
        );
        return new CuentaResponseDTO(cuenta);
    }

    @Override
    public double consultarSaldo(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No se encontro el cuenta con id: " + id)
        );
        return cuenta.getSaldo();
    }
}
