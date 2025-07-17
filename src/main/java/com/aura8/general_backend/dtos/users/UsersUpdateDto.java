package com.aura8.general_backend.dtos.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UsersUpdateDto {

    @Schema(description = "Id do usuario", example = "1")
    private Integer id;
    @Schema(description = "Nome do usuário", example = "João")
    private String username;
    @Email
    @Schema(description = "Email do usuário", example = "aura@gmail.com")
    private String email;
    @Schema(description = "Senha do usuário", example = "Th1sPassword!")
    private String password;
    @Schema(description = "Telefone do usuário", example = "11912345678")
    private String phone;
    @Schema(description = "Data de nascimento do usuário", example = "1990-01-01T00:00:00")
    private LocalDateTime dateOfBirth;
    @Schema(description = "Id da role do usuario", example = "1")
    private Integer roleId;
    @Schema(description = "Observação sobre o usuario", example = "Tem alergia")
    private String observation;

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservatio(String observation) {
        this.observation = observation;
    }
}
