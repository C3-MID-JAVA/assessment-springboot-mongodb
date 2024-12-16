package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.UserRequestDTO;
import ec.com.example.bank_account.dto.UserResponseDTO;
import ec.com.example.bank_account.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User RESTful", description = "Endpoints for user management.")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create new user", description = "Create a new user from the request data.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Body with the user data to be created", required = true)
            @Valid @RequestBody UserRequestDTO user) {
        UserResponseDTO response = userService.createUser(user);
        return response != null ?
                ResponseEntity.status(201).body(response) :
                ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get all users", description = "Get all registered users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully obtained all registered users."),
            @ApiResponse(responseCode = "404", description = "No resources found."),
            @ApiResponse(responseCode = "500", description = "Internal application problems.")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return !response.isEmpty() ?
                ResponseEntity.ok().body(response) :
                ResponseEntity.noContent().build();

    }

}