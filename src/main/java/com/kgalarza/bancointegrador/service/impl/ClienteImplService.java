package com.kgalarza.bancointegrador.service.impl;

import com.kgalarza.bancointegrador.mapper.ClienteMapper;
import com.kgalarza.bancointegrador.model.dto.ClienteInDto;
import com.kgalarza.bancointegrador.model.dto.ClienteOutDto;
import com.kgalarza.bancointegrador.model.entity.Cliente;
import com.kgalarza.bancointegrador.repository.ClienteRepository;
import com.kgalarza.bancointegrador.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteImplService implements ClienteService {
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteImplService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteOutDto guardarCliente(ClienteInDto clienteInDto) {
        Cliente cliente = ClienteMapper.toEntity(clienteInDto);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        return ClienteMapper.toDto(clienteGuardado);
    }

    @Override
    public List<ClienteOutDto> obtenerTodos() {
        return clienteRepository.findAll().stream()
                .map(ClienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteOutDto obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public ClienteOutDto actualizarCliente(Long id, ClienteInDto clienteInDto) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteInDto.getNombre());
                    cliente.setApellido(clienteInDto.getApellido());
                    cliente.setEmail(clienteInDto.getEmail());
                    cliente.setTelefono(clienteInDto.getTelefono());
                    cliente.setDireccion(clienteInDto.getDireccion());
                    cliente.setFechaNacimiento(clienteInDto.getFechaNacimiento());
                    Cliente clienteActualizado = clienteRepository.save(cliente);
                    return ClienteMapper.toDto(clienteActualizado);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
