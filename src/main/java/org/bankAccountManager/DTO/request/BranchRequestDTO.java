package org.bankAccountManager.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BranchRequestDTO {
    private int id;
    @NotNull(message = "Branch name is required")
    @NotBlank(message = "Branch name cannot be blank")
    @Size(min = 3, max = 100, message = "Branch name must be between 3 and 100 characters")
    private String name;
    @NotNull(message = "Branch address is required")
    @NotBlank(message = "Branch address cannot be blank")
    @Size(min = 5, max = 200, message = "Branch address must be between 5 and 200 characters")
    private String address;
    @NotNull(message = "Branch phone is required")
    @NotBlank(message = "Branch phone cannot be blank")
    private String phone;

    public BranchRequestDTO(String address, int id, String name, String phone) {
        this.address = address;
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public BranchRequestDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
