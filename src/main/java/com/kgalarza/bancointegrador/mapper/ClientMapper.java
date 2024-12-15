package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static Client toEntity(ClientInDto dto) {
        Client cliente = new Client();
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        return cliente;
    }

    public static ClientOutDto toDto(Client cliente) {
        ClientOutDto dto = new ClientOutDto();
        dto.setId(cliente.getId());
        dto.setIdentificacion(cliente.getIdentificacion());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setDireccion(cliente.getDireccion());
        dto.setFechaNacimiento(cliente.getFechaNacimiento());
//        if (cliente.getCuentas() != null) {
//            List<Long> cuentasIds = cliente.getCuentas().stream()
//                    .map(Cuenta::getId)
//                    .collect(Collectors.toList());
//            dto.setCuentasIds(cuentasIds);
//        }
        return dto;
    }
}
