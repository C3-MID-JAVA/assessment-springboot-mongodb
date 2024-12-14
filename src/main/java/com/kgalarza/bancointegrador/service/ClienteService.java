package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.ClienteInDto;
import com.kgalarza.bancointegrador.model.dto.ClienteOutDto;
import com.kgalarza.bancointegrador.model.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ClienteService {


    ClienteOutDto guardarCliente(ClienteInDto clienteInDto);

    List<ClienteOutDto> obtenerTodos();

    ClienteOutDto obtenerPorId(String id);

    ClienteOutDto actualizarCliente(String id, ClienteInDto clienteInDto);

    void eliminarCliente(String id);
}
