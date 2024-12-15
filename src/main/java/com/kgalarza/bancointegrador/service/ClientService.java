package com.kgalarza.bancointegrador.service;

import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {


    ClientOutDto saveClient(ClientInDto clienteInDto);

    List<ClientOutDto> getAll();

    ClientOutDto getById(String id);

    ClientOutDto updateClient(String id, ClientInDto clienteInDto);

    void deleteClient(String id);
}
