package com.aura8.general_backend.clean_arch.core.domain;

import java.util.List;

public class Role {
    private Integer id;
    private String name;
    private List<Users> users;

    public Role(String name) {
        this.name = name;
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(Integer id, String name, List<Users> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String validateString(String str) {
        if(str == null || str.trim().isEmpty() || str.length() < 3) throw new IllegalArgumentException("String inválida: " + str);
        return str;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
