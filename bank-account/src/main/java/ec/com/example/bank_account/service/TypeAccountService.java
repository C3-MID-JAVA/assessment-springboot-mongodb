package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.TypeAccountRequestDTO;
import ec.com.example.bank_account.dto.TypeAccountResponseDTO;

import java.util.List;

public interface TypeAccountService {

    TypeAccountResponseDTO createTypeAccount(TypeAccountRequestDTO typeAccount);

    List<TypeAccountResponseDTO> getAllTypeAccount();

}
