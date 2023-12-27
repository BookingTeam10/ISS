package com.booking.ProjectISS.dto.users;

import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private static final Long serialVersionUID = -8178366724097283480L;
    private Long id;
    private String email;

    private String name;
    private String surname;

    private boolean isReported;

    private boolean blocked;


    public UserDTO() {
    }

    public UserDTO(Long id,String email, String name, String surname, boolean isReported, boolean isBlocked) {
        this();
        this.id=id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.isReported=isReported;
        this.blocked=isBlocked;
    }

    public UserDTO(User u) {
        this();
        this.id=u.getId();
        this.email = u.getEmail();
        this.name = u.getName();
        this.surname = u.getSurname();
        this.isReported=u.isReported();
        this.blocked=u.isBlocked();
    }

    public UserDTO(GuestDTO g){
        this();
        this.id=g.getId();
        this.email = g.getEmail();
        this.name = g.getName();
        this.surname = g.getSurname();
    }

    public UserDTO(OwnerDTO g){
        this();
        this.id=g.getId();
        this.email = g.getEmail();
        this.name = g.getName();
        this.surname = g.getSurname();
    }

    public UserDTO(Long id,String email){
        this();
        this.id=id;
        this.email=email;
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

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isReported() {return isReported;}

    public void setReported(boolean reported) {
        isReported = reported;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", rep=" + isReported +
                ", blocked=" + blocked +
                '}';
    }

    public void copyValues(User u) {
        this.setEmail(u.getEmail());
        this.setName(u.getName());
        this.setSurname(u.getSurname());
        this.setReported(u.isReported());
        this.setBlocked(u.isBlocked());
    }

    public static class AdministratorDTO implements Serializable {
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
    }
}
