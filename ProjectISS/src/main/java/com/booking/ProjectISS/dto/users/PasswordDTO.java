package com.booking.ProjectISS.dto.users;

import lombok.Getter;

import java.util.Objects;

@Getter
public class PasswordDTO {

    private String password;

    public  PasswordDTO(){}
    public PasswordDTO(String password) {
        this.password = password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordDTO that = (PasswordDTO) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
