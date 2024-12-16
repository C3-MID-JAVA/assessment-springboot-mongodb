package ec.com.example.bank_account.mapper;

import ec.com.example.bank_account.dto.AccountRequestDTO;
import ec.com.example.bank_account.dto.AccountResponseDTO;
import ec.com.example.bank_account.entity.Account;
import ec.com.example.bank_account.entity.TypeAccount;
import ec.com.example.bank_account.entity.User;
import ec.com.example.bank_account.exception.RecordNotFoundException;
import ec.com.example.bank_account.repository.TypeAccountRepository;
import ec.com.example.bank_account.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    private final UserRepository userRepository;
    private final TypeAccountRepository typeAccountRepository;
    private final UserMapper userMapper;
    private final TypeAccountMapper typeAccountMapper;

    public AccountMapper(UserRepository userRepository, TypeAccountRepository typeAccountRepository, UserMapper userMapper, TypeAccountMapper typeAccountMapper) {
        this.userRepository = userRepository;
        this.typeAccountRepository = typeAccountRepository;
        this.userMapper = userMapper;
        this.typeAccountMapper = typeAccountMapper;
    }

    public Account mapToEntity(AccountRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setNumber(dto.getNumber());
        account.setAvailableBalance(dto.getAvailableBalance());
        account.setRetainedBalance(dto.getRetainedBalance());
        account.setStatus(dto.getStatus());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RecordNotFoundException("User not found."));
        account.setUser(user);

        TypeAccount typeAccount = typeAccountRepository
                .findById(dto.getTypeAccountId())
                .orElseThrow(() -> new RecordNotFoundException("TypeAccount not found."));
        account.setTypeAccount(typeAccount);

        return account;
    }

    public AccountResponseDTO mapToDTO(Account account) {
        if (account == null) {
            return null;
        }

        return new AccountResponseDTO(
                account.getNumber(),
                account.getAvailableBalance(),
                account.getRetainedBalance(),
                account.getStatus(),
                userMapper.mapToDTO(account.getUser()),
                typeAccountMapper.mapToDTO(account.getTypeAccount())
        );
    }
}
