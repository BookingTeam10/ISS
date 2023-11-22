package com.booking.ProjectISS.model.users;

import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
//@Entity
////If annotation is @MappedSuperclass => 2 table (Guest+User), if annotation is @Entity => 1table (User=Guest+Host)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User implements Serializable {
    //@javax.persistence.Id
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)         //increment add id+1, e.g. if previous id was 5, the next will be 6
    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private boolean blocked;

    private boolean isReported;

    public User(Long id,String email, String password, String name, String surname, String phone, String address) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.blocked = false;
        this.isReported=false;
    }

    public User(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep,boolean blocked) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.isReported=rep;
        this.blocked = blocked;
    }

    public User() {
    }

    public User(Long id) {
        this.id=id;
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

    public boolean isReported() {return isReported;}

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

    public void setReported(boolean reported) {
        this.isReported = reported;
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

    public void copyValues(User user) {
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setName(user.getName());
        this.setSurname(user.getSurname());
        this.setPhone(user.getPhone());
        this.setAddress(user.getAddress());
        this.setReported(user.isReported());
        this.setBlocked(user.isBlocked());
    }
}
