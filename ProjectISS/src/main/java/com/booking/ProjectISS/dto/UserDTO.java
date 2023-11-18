package com.booking.ProjectISS.dto;

import com.booking.ProjectISS.model.Guest;
import com.booking.ProjectISS.model.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private static final Long serialVersionUID = -8178366724097283480L;
    private Long id;
    private String email;

    private String name;
    private String surname;


    public UserDTO() {
    }

    public UserDTO(Long id,String email, String name, String surname) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public UserDTO(User u) {
        this();
        this.id=u.getId();
        this.email = u.getEmail();
        this.name = u.getName();
        this.surname = u.getSurname();
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

    public void setEmail(String email) {
        this.email = email;
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
    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
