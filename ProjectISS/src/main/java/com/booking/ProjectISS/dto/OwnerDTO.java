package com.booking.ProjectISS.dto;

import com.booking.ProjectISS.model.Owner;

import java.io.Serializable;

public class OwnerDTO implements Serializable {
    private static final Long serialVersionUID = -8178366724097283480L;
    private Long id;
    private String email;

    private String name;
    private String surname;


    public OwnerDTO() {
    }

    public OwnerDTO(Long id,String email, String name, String surname) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    public OwnerDTO(Owner o) {
        this();
        this.id=o.getId();
        this.email = o.getEmail();
        this.name = o.getName();
        this.surname = o.getSurname();
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
        return "OwnerDTO{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
