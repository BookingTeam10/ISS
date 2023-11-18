package com.booking.ProjectISS.dto;

import com.booking.ProjectISS.model.Administrator;
import com.booking.ProjectISS.model.Guest;

import java.io.Serializable;

public class AdministratorDTO implements Serializable {
    private static final Long serialVersionUID = -8178366724097283480L;
    private Long id;
    private String email;

    public AdministratorDTO() {
    }

    public AdministratorDTO(Long id,String email) {
        this();
        this.id=id;
        this.email = email;
    }

    public AdministratorDTO(Administrator a) {
        this();
        this.id=a.getId();
        this.email = a.getEmail();
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
