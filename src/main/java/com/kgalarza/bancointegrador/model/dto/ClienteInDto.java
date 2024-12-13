package com.kgalarza.bancointegrador.model.dto;

import com.kgalarza.bancointegrador.model.entity.Cuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public class ClienteInDto {

    @NotBlank(message = "La identificación no puede estar vacía")
    @Size(min = 10, max = 10, message = "La identificación debe tener exactamente 10 caracteres.")
    @Pattern(regexp = "\\d{10}", message = "La identificación debe contener solo números.")
    private String identificacion;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @Email(message = "El correo electrónico no tiene un formato válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "El número de teléfono no tiene un formato válido")
    private String telefono;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;

    //@Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
