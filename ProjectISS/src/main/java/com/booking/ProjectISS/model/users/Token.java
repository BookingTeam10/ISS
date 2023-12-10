package com.booking.ProjectISS.model.users;

import jakarta.persistence.Transient;

public class Token {

    @Transient
    private String jwt;

    public Token(){}

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
