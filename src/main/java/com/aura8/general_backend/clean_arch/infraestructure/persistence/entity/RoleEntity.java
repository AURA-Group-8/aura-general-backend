package com.aura8.general_backend.clean_arch.infraestructure.persistence.entity;

import com.aura8.general_backend.infraestructure.entities.Users;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany
    @JsonManagedReference
    List<Users> users;

    private String name;
    private Boolean deleted = false;

    public RoleEntity() {
    }

    public RoleEntity(Integer id, List<Users> users, String name, Boolean deleted) {
        this.id = id;
        this.users = users;
        this.name = name;
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
