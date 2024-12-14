package com.bankmanagement.bankmanagement.service.impl;

import com.bankmanagement.bankmanagement.dto.UserRequestDTO;
import com.bankmanagement.bankmanagement.dto.UserResponseDTO;
import com.bankmanagement.bankmanagement.mapper.UserMapper;
import com.bankmanagement.bankmanagement.model.User;
import com.bankmanagement.bankmanagement.repository.UserRespository;
import com.bankmanagement.bankmanagement.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRespository userRespository;

    public UserServiceImpl(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO userRequestDTO) {
        User savedUser = userRespository.save(UserMapper.toEntity(userRequestDTO));
        return UserMapper.fromEntity(savedUser);
    }
}
