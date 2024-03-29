package com.booking.ProjectISS.dto.users;

import com.booking.ProjectISS.model.users.Administrator;

import java.io.Serializable;

public class AdministratorDTO implements Serializable {
    private static final Long serialVersionUID = -8178366724097283480L;
    private Long id;
    private String email;
    private String name;
    private String surname;

    public AdministratorDTO() {
    }

    public AdministratorDTO(Long id,String email,String name,String surname) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public AdministratorDTO(Administrator a) {
        this();
        this.id=a.getId();
        this.email = a.getEmail();
        this.name = a.getName();
        this.surname = a.getSurname();
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
    @Override
    public String toString() {
        return "AdministratorDTO{" +
                "email='" + email + '\'';
    }
}