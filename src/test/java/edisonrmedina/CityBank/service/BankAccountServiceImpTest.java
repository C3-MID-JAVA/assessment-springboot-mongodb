package edisonrmedina.CityBank.service;

import edisonrmedina.CityBank.entity.bank.BankAccount;
import edisonrmedina.CityBank.repository.BankAccountRepository;
import edisonrmedina.CityBank.service.impl.BankAccountServiceImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImpTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImp bankAccountServiceImp;

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount("John Doe", BigDecimal.valueOf(1000));
        bankAccount.setId("123");
    }

    @Test
    void testGetBankAccount_PositiveCase() {
        // Arrange
        Mockito.when(bankAccountRepository.findById("123")).thenReturn(Optional.of(bankAccount));

        // Act
        Optional<BankAccount> result = bankAccountServiceImp.getBankAccount("123");

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("John Doe", result.get().getAccountHolder());
        Assertions.assertEquals(BigDecimal.valueOf(1000), result.get().getBalance());
    }

    @Test
    void testGetBankAccount_NegativeCase() {
        // Arrange
        Mockito.when(bankAccountRepository.findById("999")).thenReturn(Optional.empty());

        // Act
        Optional<BankAccount> result = bankAccountServiceImp.getBankAccount("999");

        // Assert
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void testRegister_PositiveCase() {
        // Arrange
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        // Act
        BankAccount result = bankAccountServiceImp.register(bankAccount);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("John Doe", result.getAccountHolder());
        Assertions.assertEquals(BigDecimal.valueOf(1000), result.getBalance());
    }

    @Test
    void testRegister_NegativeCase_MissingAccountHolder() {
        // Arrange
        BankAccount invalidBankAccount = new BankAccount();
        invalidBankAccount.setBalance(BigDecimal.valueOf(1000)); // Balance está presente, pero falta el titular

        // Act & Assert
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bankAccountServiceImp.register(invalidBankAccount);
        });

        // Verificar el mensaje de la excepción
        Assertions.assertEquals("Account holder cannot be null or empty", exception.getMessage());
    }

    @Test
    void testUpdateBankAccount_PositiveCase() {
        // Arrange
        Mockito.when(bankAccountRepository.save(bankAccount)).thenReturn(bankAccount);

        // Act
        bankAccountServiceImp.updateBankAccount(Optional.of(bankAccount));

        // Assert
        Mockito.verify(bankAccountRepository, Mockito.times(1)).save(bankAccount);
    }

    @Test
    void testUpdateBankAccount_NullAccount() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                bankAccountServiceImp.updateBankAccount(null));
    }

    @Test
    void testUpdateBankAccount_MissingId() {
        // Arrange
        BankAccount accountWithoutId = new BankAccount("Jane Doe", BigDecimal.valueOf(500));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                bankAccountServiceImp.updateBankAccount(Optional.of(accountWithoutId)));
    }

}
