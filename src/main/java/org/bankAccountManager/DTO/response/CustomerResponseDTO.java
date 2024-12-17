package org.bankAccountManager.DTO.response;

import jakarta.validation.constraints.Email;
import org.bankAccountManager.entity.Account;

import java.util.List;

public class CustomerResponseDTO {
    private int id;
    private String first_name;
    private String last_name;
    @Email
    private String email;
    private String phone;
    private String address;
    private List<AccountResponseDTO> accounts;

    public CustomerResponseDTO(String address, String email, String first_name, int id, String last_name, String phone, List<AccountResponseDTO> accounts) {
        this.address = address;
        this.email = email;
        this.first_name = first_name;
        this.id = id;
        this.last_name = last_name;
        this.phone = phone;
        this.accounts = accounts;
    }

    public CustomerResponseDTO() {
    }

    public List<AccountResponseDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResponseDTO> accounts) {
        this.accounts = accounts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
