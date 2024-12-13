package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.ClienteInDto;
import com.kgalarza.bancointegrador.model.dto.ClienteOutDto;
import com.kgalarza.bancointegrador.model.entity.Cliente;
import com.kgalarza.bancointegrador.model.entity.Cuenta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    public static Cliente toEntity(ClienteInDto dto) {
        Cliente cliente = new Cliente();
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        return cliente;
    }

    public static ClienteOutDto toDto(Cliente cliente) {
        ClienteOutDto dto = new ClienteOutDto();
        dto.setId(cliente.getId());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
        if (cliente.getCuentas() != null) {
            List<Long> cuentasIds = cliente.getCuentas().stream()
                    .map(Cuenta::getId)
                    .collect(Collectors.toList());
            dto.setCuentasIds(cuentasIds);
        }
        return dto;
    }
}
