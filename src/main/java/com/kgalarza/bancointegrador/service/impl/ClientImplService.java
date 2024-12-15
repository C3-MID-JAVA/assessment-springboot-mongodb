package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.ClientMapper;
import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import com.kgalarza.bancointegrador.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientImplService implements ClientService {

    private final ClientRepository clienteRepository;

    @Autowired
    public ClientImplService(ClientRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClientOutDto saveClient(ClientInDto clientInDto) {
        Client client = ClientMapper.toEntity(clientInDto);
        Client savedClient = clienteRepository.save(client);
        return ClientMapper.toDto(savedClient);
    }

    @Override
    public List<ClientOutDto> getAll() {
        List<ClientOutDto> clients = clienteRepository.findAll().stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());

        if (clients.isEmpty()) {
            throw new ResourceNotFoundException("No existen clientes registrados.");
        }

        return clients;
    }



    @Override
    public ClientOutDto getById(String id) {

        return clienteRepository.findById(id)
                .map(ClientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }


    @Override
    public ClientOutDto updateClient(String id, ClientInDto clientInDto) {
        return clienteRepository.findById(id)
                .map(client -> {

                    Client clienteActualizado = ClientMapper.toEntity(clientInDto);
                    clienteActualizado.setId(client.getId());
                    clienteActualizado.setIdentification(client.getIdentification());

                    Client updatedClient = clienteRepository.save(clienteActualizado);

                    return ClientMapper.toDto(updatedClient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public void deleteClient(String id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }


}
