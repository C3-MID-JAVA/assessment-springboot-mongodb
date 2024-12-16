package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.UserRequestDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void createUser_ShouldReturnCreatedStatus_WhenUserIsCreatedSuccessfully() {
        UserRequestDTO userRequest = new UserRequestDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co","Diego123.", "ACTIVE");
        UserResponseDTO userResponse = new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE");
        when(userService.createUser(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void createUser_ShouldReturnBadRequestStatus_WhenUserCreationFails() {
        UserRequestDTO userRequest = new UserRequestDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co","Diego123.", null);
        when(userService.createUser(userRequest)).thenReturn(null);

        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void getAllUsers_ShouldReturnOkStatus_WhenUsersAreFound() {
        List<UserResponseDTO> users = List.of(new UserResponseDTO("Diego", "Loor",
                "1310000000", "diego.loor@sofka.com.co", "ACTIVE"));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getAllUsers_ShouldReturnNoContentStatus_WhenNoUsersAreFound() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserResponseDTO>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).getAllUsers();
    }
}