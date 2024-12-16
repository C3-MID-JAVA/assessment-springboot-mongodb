package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.UserRequestDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO user);

    List<UserResponseDTO> getAllUsers();

}