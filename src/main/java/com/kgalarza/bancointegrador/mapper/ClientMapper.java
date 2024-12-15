package com.kgalarza.bancointegrador.mapper;

import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public static Client toEntity(ClientInDto dto) {
        Client cliente = new Client();
        cliente.setIdentification(dto.getIdentificacion());
        cliente.setFirstName(dto.getNombre());
        cliente.setLastName(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setPhone(dto.getTelefono());
        cliente.setAddress(dto.getDireccion());
        cliente.setBirthDate(dto.getFechaNacimiento());
        return cliente;
    }

    public static ClientOutDto toDto(Client cliente) {
        ClientOutDto dto = new ClientOutDto();
        dto.setId(cliente.getId());
        dto.setIdentificacion(cliente.getIdentification());
        dto.setNombre(cliente.getFirstName());
        dto.setApellido(cliente.getLastName());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getPhone());
        dto.setDireccion(cliente.getAddress());
        dto.setFechaNacimiento(cliente.getBirthDate());

        return dto;
    }
}
