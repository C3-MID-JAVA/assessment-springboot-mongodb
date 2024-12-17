package org.bankAccountManager.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.bankAccountManager.entity.Account;

import java.util.List;

public class CustomerRequestDTO {
    private int id;
    @NotNull(message = "Customer's first name is required")
    @NotBlank(message = "Customer's first name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String first_name;
    @NotNull(message = "Customer's last name is required")
    @NotBlank(message = "Customer's last name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String last_name;
    @Email(message = "Invalid email format")
    @NotNull(message = "Customer's email is required")
    @NotBlank(message = "Customer's email cannot be blank")
    private String email;
    private String phone;
    @Size(max = 200, message = "Address can be a maximum of 200 characters")
    private String address;
    @NotNull(message = "Customer's accounts is required")
    private List<AccountRequestDTO> accounts;

    public CustomerRequestDTO(String address, String email, String first_name, int id, String last_name, String phone, List<AccountRequestDTO> accounts) {
        this.address = address;
        this.email = email;
        this.first_name = first_name;
        this.id = id;
        this.last_name = last_name;
        this.phone = phone;
        this.accounts = accounts;
    }

    public CustomerRequestDTO() {
    }

    public @NotNull(message = "Customer's accounts is required") List<AccountRequestDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(@NotNull(message = "Customer's accounts is required") List<AccountRequestDTO> accounts) {
        this.accounts = accounts;
    }

    public @Size(max = 200, message = "Address can be a maximum of 200 characters") String getAddress() {
        return address;
    }

    public void setAddress(@Size(max = 200, message = "Address can be a maximum of 200 characters") String address) {
        this.address = address;
    }

    public @Email(message = "Invalid email format") @NotNull(message = "Customer's email is required") @NotBlank(message = "Customer's email cannot be blank") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid email format") @NotNull(message = "Customer's email is required") @NotBlank(message = "Customer's email cannot be blank") String email) {
        this.email = email;
    }

    public @NotNull(message = "Customer's first name is required") @NotBlank(message = "Customer's first name cannot be blank") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(@NotNull(message = "Customer's first name is required") @NotBlank(message = "Customer's first name cannot be blank") @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters") String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "Customer's last name is required") @NotBlank(message = "Customer's last name cannot be blank") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String getLast_name() {
        return last_name;
    }

    public void setLast_name(@NotNull(message = "Customer's last name is required") @NotBlank(message = "Customer's last name cannot be blank") @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters") String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
