package ec.com.example.bank_account.service.impl;

import ec.com.example.bank_account.dto.UserRequestDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.entity.User;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.UserMapper;
import ec.com.example.bank_account.repository.UserRepository;
import ec.com.example.bank_account.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO user) {
        return userMapper.mapToDTO(userRepository.save(userMapper.mapToEntity(user)));
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<UserResponseDTO> response = userRepository.findAll().stream()
                .map(userMapper::mapToDTO).collect(Collectors.toList());

        if (response.isEmpty()) {
            throw new EmptyCollectionException("No users records found.");
        }
        return response;
    }
}