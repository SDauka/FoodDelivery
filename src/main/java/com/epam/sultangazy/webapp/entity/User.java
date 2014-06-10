package com.epam.sultangazy.webapp.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phone;
    private Role role;

    public enum Role {
        ADMIN,
        USER,
        RESTORATOR,
        UNREGISTERED
    }

    public User() {
    }

    public User(String email, String name, String password, String address, String phone, Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public User(int id, String email, String password, String name, String address, String phone, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public User(String name, String address, String password, String phone) {
        this.name = name;
        this.address = address;
        this.password = password;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return User.class.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRoleOrdinal(int roleOrdinal) {
        if (roleOrdinal == 0) {
            this.role = Role.ADMIN;
        } else if (roleOrdinal == 1) {
            this.role = Role.USER;
        } else if (roleOrdinal == 2) {
            this.role = Role.RESTORATOR;
        }
    }
}
