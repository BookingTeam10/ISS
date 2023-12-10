package com.booking.ProjectISS.dto.users;

import java.io.Serializable;
import jakarta.persistence.*;

public class LoginDTO implements Serializable {
    private static final Long serialVersionUID = -8178366724097283480L;

    private String email;

    private String password;

    @Transient
    private String jwt;

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginDTO(){
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
    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
