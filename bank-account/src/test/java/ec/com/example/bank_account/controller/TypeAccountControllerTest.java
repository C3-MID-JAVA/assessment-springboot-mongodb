package ec.com.example.bank_account.controller;

import ec.com.example.bank_account.dto.TypeAccountRequestDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;
import ec.com.example.bank_account.service.TypeAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ExtendWith(MockitoExtension.class)
public class TypeAccountControllerTest {

    @Mock
    private TypeAccountService typeAccountService;

    @InjectMocks
    private TypeAccountController typeAccountController;

    @Test
    void createTypeAccount_ShouldReturnCreatedStatus_WhenAccountTypeIsCreatedSuccessfully() throws Exception {
        TypeAccountRequestDTO typeAccountRequestDTO = new TypeAccountRequestDTO("Debit account",
                "User debit account.", "ACTIVE");
        TypeAccountResponseDTO typeAccountResponseDTO = new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE");
        when(typeAccountService.createTypeAccount(typeAccountRequestDTO))
                .thenReturn(typeAccountResponseDTO);

        ResponseEntity<TypeAccountResponseDTO> response = typeAccountController.createTypeAccount(typeAccountRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(typeAccountResponseDTO, response.getBody());
        verify(typeAccountService, times(1)).createTypeAccount(typeAccountRequestDTO);
    }

    @Test
    void createTypeAccount_ShouldReturnBadRequest_WhenAccountTypeCreationFails() throws Exception {
        TypeAccountRequestDTO typeAccountRequestDTO = new TypeAccountRequestDTO("Debit account",
                "User debit account.", null);
        when(typeAccountService.createTypeAccount(typeAccountRequestDTO))
                .thenReturn(null);

        ResponseEntity<TypeAccountResponseDTO> response = typeAccountController.createTypeAccount(typeAccountRequestDTO);

        assertNotNull(response);
        assertEquals(BAD_REQUEST, response.getStatusCode());
        verify(typeAccountService, times(1)).createTypeAccount(typeAccountRequestDTO);
    }

    @Test
    void getTypesAccount_ShouldReturnListOfAccountTypes_WhenAccountsExist() throws Exception {
        List<TypeAccountResponseDTO> typesAccount = List.of(new TypeAccountResponseDTO("Debit account",
                "User debit account.", "ACTIVE"));
        when(typeAccountService.getAllTypeAccount()).thenReturn(typesAccount);

        ResponseEntity<List<TypeAccountResponseDTO>> response = typeAccountController.getTypesAccount();

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(typesAccount, response.getBody());
        verify(typeAccountService, times(1)).getAllTypeAccount();
    }

    @Test
    void getTypesAccount_ShouldReturnNoContent_WhenNoAccountTypesExist() throws Exception {
        when(typeAccountService.getAllTypeAccount()).thenReturn(Collections.emptyList());

        ResponseEntity<List<TypeAccountResponseDTO>> response = typeAccountController.getTypesAccount();

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(typeAccountService, times(1)).getAllTypeAccount();
    }

}