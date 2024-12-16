package ec.com.example.bank_account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class AccountResponseDTO implements Serializable {

    private String number;
    private BigDecimal availableBalance;
    private BigDecimal retainedBalance;
    private String status;

    private UserResponseDTO user;
    private TypeAccountResponseDTO typeAccount;

}