package com.booking.ProjectISS.dto.users;

import com.booking.ProjectISS.enums.TypeUser;

import java.io.Serializable;

public class RegisterDTO implements Serializable {
    private static final long serialVersionUID = -8178366724097283480L;
    private String email;
    private String password;
    private String name;
    private String surname;
    private TypeUser typeUser;
    private String phone;
    private String address;

    public RegisterDTO(String email, String password, String name, String surname, TypeUser typeUser, String phone, String address) {
        this();
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.typeUser = typeUser;
        this.phone = phone;
        this.address = address;
    }

    public RegisterDTO() {

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
