package com.aura8.general_backend.clean_arch.core.domain;

import com.aura8.general_backend.clean_arch.core.domain.attribute.Email;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Password;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Phone;
import com.aura8.general_backend.clean_arch.core.domain.attribute.Username;

import java.time.LocalDate;

public class Users {
    private Integer id;
    private Username username;
    private Email email;
    private Password password;
    private Phone phone;
    private LocalDate dateOfBirth;
    private String observation;
    private Integer roleId;
    private Boolean deleted = false;

    public Users(Username username, Email email, Password password, Phone phone, Integer roleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.roleId = roleId;
    }

    public Users(Username username, Email email, Password password, Phone phone, LocalDate dateOfBirth, Integer roleId) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.roleId = roleId;
    }

    public Users(Integer id, Username username, Email email, Password password, Phone phone, LocalDate dateOfBirth, String observation, Integer roleId, Boolean deleted) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.observation = observation;
        this.roleId = roleId;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }

    @Override
    public String toString() {
        return "Users{" +
                "username=" + username +
                ", email=" + email +
                ", password=" + password +
                ", phone=" + phone +
                ", dateOfBirth=" + dateOfBirth +
                ", observation='" + observation + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
