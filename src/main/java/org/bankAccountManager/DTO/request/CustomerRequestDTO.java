package org.bankAccountManager.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    public CustomerRequestDTO(String address, String email, String first_name, int id, String last_name, String phone) {
        this.address = address;
        this.email = email;
        this.first_name = first_name;
        this.id = id;
        this.last_name = last_name;
        this.phone = phone;
    }

    public CustomerRequestDTO() {
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
