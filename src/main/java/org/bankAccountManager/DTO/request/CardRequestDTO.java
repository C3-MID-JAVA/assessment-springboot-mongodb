package org.bankAccountManager.DTO.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

public class CardRequestDTO {
    private int id;
    @NotNull(message = "Card's number is required")
    @NotBlank(message = "Card's number cannot be blank")
    @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits")
    @Pattern(regexp = "^[0-9]+$", message = "Card number must contain only digits")
    private String card_number;
    @Size(max = 50, message = "Card type can be a maximum of 50 characters")
    private String card_type;
    @NotNull(message = "Card's expiration date is required")
    private Timestamp expiration_date;
    @NotNull(message = "CVV is required")
    @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits")
    private String cvv;

    public CardRequestDTO(String card_number, String card_type, String cvv, Timestamp expiration_date, int id) {
        this.card_number = card_number;
        this.card_type = card_type;
        this.cvv = cvv;
        this.expiration_date = expiration_date;
        this.id = id;
    }

    public CardRequestDTO() {
    }

    public @NotNull(message = "Card's number is required") @NotBlank(message = "Card's number cannot be blank") @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits") @Pattern(regexp = "^[0-9]+$", message = "Card number must contain only digits") String getCard_number() {
        return card_number;
    }

    public void setCard_number(@NotNull(message = "Card's number is required") @NotBlank(message = "Card's number cannot be blank") @Size(min = 13, max = 19, message = "Card number must be between 13 and 19 digits") @Pattern(regexp = "^[0-9]+$", message = "Card number must contain only digits") String card_number) {
        this.card_number = card_number;
    }

    public @Size(max = 50, message = "Card type can be a maximum of 50 characters") String getCard_type() {
        return card_type;
    }

    public void setCard_type(@Size(max = 50, message = "Card type can be a maximum of 50 characters") String card_type) {
        this.card_type = card_type;
    }

    public @NotNull(message = "CVV is required") @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits") String getCvv() {
        return cvv;
    }

    public void setCvv(@NotNull(message = "CVV is required") @Pattern(regexp = "^[0-9]{3,4}$", message = "CVV must be 3 or 4 digits") String cvv) {
        this.cvv = cvv;
    }

    public @NotNull(message = "Card's expiration date is required") Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(@NotNull(message = "Card's expiration date is required") Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
