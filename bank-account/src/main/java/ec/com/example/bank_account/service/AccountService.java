package ec.com.example.bank_account.service;

import ec.com.example.bank_account.dto.AccountRequestDTO;
import ec.com.example.bank_account.dto.AccountResponseDTO;

import java.util.List;

public interface AccountService {

    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

   List<AccountResponseDTO> getAllAccounts();

}
