package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {


    ClientOutDto guardarCliente(ClientInDto clienteInDto);

    List<ClientOutDto> obtenerTodos();

    ClientOutDto obtenerPorId(String id);

    ClientOutDto actualizarCliente(String id, ClientInDto clienteInDto);

    void eliminarCliente(String id);
}
