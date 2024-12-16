package ec.com.example.bank_account.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class TransactionResponseDTO implements Serializable {

    private BigDecimal value;
    private Date date;
    private String status;
    private AccountResponseDTO account;
    private TypeTransactionResponseDTO typeTransaction;

}