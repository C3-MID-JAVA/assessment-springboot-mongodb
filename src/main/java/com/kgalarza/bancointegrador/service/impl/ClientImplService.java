package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.exception.ResourceNotFoundException;
import com.kgalarza.bancointegrador.mapper.ClientMapper;
import com.kgalarza.bancointegrador.model.dto.ClientInDto;
import com.kgalarza.bancointegrador.model.dto.ClientOutDto;
import com.kgalarza.bancointegrador.model.entity.Client;
import com.kgalarza.bancointegrador.repository.ClientRepository;
import com.kgalarza.bancointegrador.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientImplService implements ClienteService {
    private final ClientRepository clienteRepository;

    @Autowired
    public ClientImplService(ClientRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClientOutDto guardarCliente(ClientInDto clienteInDto) {
        Client cliente = ClientMapper.toEntity(clienteInDto);
        Client clienteGuardado = clienteRepository.save(cliente);
        return ClientMapper.toDto(clienteGuardado);
    }

    @Override
    public List<ClientOutDto> obtenerTodos() {
        List<ClientOutDto> clientes = clienteRepository.findAll().stream()
                .map(ClientMapper::toDto)
                .collect(Collectors.toList());

        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("No existen clientes registrados.");
        }

        return clientes;
    }



    @Override
    public ClientOutDto obtenerPorId(String id) {

        return clienteRepository.findById(id)
                .map(ClientMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }


    @Override
    public ClientOutDto actualizarCliente(String id, ClientInDto clienteInDto) {
        return clienteRepository.findById(id)
                .map(cliente -> {

                    Client clienteActualizado = ClientMapper.toEntity(clienteInDto);
                    clienteActualizado.setId(cliente.getId());
                    clienteActualizado.setIdentificacion(cliente.getIdentificacion());

                    Client clienteActualizadoResp = clienteRepository.save(clienteActualizado);

                    return ClientMapper.toDto(clienteActualizadoResp);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public void eliminarCliente(String id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
