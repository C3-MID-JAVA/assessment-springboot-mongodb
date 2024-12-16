package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.UserRequestDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.entity.User;
import ec.com.example.bank_account.exception.EmptyCollectionException;
import ec.com.example.bank_account.mapper.UserMapper;
import ec.com.example.bank_account.repository.UserRepository;
import ec.com.example.bank_account.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setCi("1310000000");
        user.setEmail("diego.loor@sofka.com.co");
        user.setFirstName("Diego");
        user.setLastName("Loor");
        user.setPassword("diego.loor@sofka.com.co");
        user.setStatus("ACTIVE");

        userRequestDTO = new UserRequestDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co","Diego123.", "ACTIVE");

        userResponseDTO = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
    }
    
    @Test
    void createUser_ShouldReturnCreatedEntity_WhenUserIsCreatedSuccessfully() {
        when(userMapper.mapToEntity(userRequestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.mapToDTO(user)).thenReturn(userResponseDTO);

        UserResponseDTO response = userService.createUser(userRequestDTO);

        assertNotNull(response);
        assertEquals(userResponseDTO, response);
        verify(userRepository, times(1)).save(user);
        verify(userMapper, times(1)).mapToDTO(user);
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers_WhenUsersAreFound() {
        List<User> users = List.of(user);
        List<UserResponseDTO> userResponseDTOs = List.of(userResponseDTO);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.mapToDTO(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            return new UserResponseDTO( user.getFirstName(), user.getLastName(), user.getCi(),
                    user.getEmail(), user.getStatus());
        });

        List<UserResponseDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(userResponseDTOs.size(), result.size());
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).mapToDTO(any(User.class));
    }

    @Test
    void getAllUsers_ShouldThrowExceptionWhenNoUsersFound() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        EmptyCollectionException exception = assertThrows(EmptyCollectionException.class, () -> userService.getAllUsers());
        assertEquals("No users records found.", exception.getMessage());
    }

}