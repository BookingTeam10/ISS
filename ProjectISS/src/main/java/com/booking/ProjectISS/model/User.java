package com.booking.ProjectISS.model;

import java.io.Serializable;

public abstract class User implements Serializable {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private boolean blocked;

    public User(Long id,String email, String password, String name, String surname, String phone, String address) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.blocked = false;
    }

    public User(Long id,String email, String password, String name, String surname, String phone, String address,boolean blocked) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.blocked = blocked;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", blocked=" + blocked +
                '}';
    }
}
