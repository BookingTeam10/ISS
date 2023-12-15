package com.booking.ProjectISS.model.users;

import com.booking.ProjectISS.enums.Role;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)         //increment add id+1, e.g. if previous id was 5, the next will be 6
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false,name="password")
    private String password;
    @Column(nullable = false,name="firstname")
    private String name;
    @Column(nullable = false,name="surname")
    private String surname;
    @Column(nullable = false,name="phone")
    private String phone;
    @Column(nullable = false,name="address")
    private String address;
    @Column(nullable = false,name="is_blocked")
    private boolean blocked;
    @Column(name="is_reported")
    private boolean isReported;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="activationc")
    private String activationCode;

    @Column(name="activatione")
    private LocalDateTime activationExpiry;

    @Transient
    private String jwt;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    public User(Long id,String email, String password, String name, String surname, String phone, String address,Role role) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.blocked = false;
        this.isReported=false;
        this.role = role;
    }

    public User(Long id,String email, String password, String name, String surname, String phone, String address,boolean rep,boolean blocked,Role role) {
        this.id=id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.address = address;
        this.isReported=rep;
        this.blocked = blocked;
        this.role=role;
    }

    public User() {
    }

    public User(Long id) {
        this.id=id;
    }

    public User(User user){
        this.id=user.id;
        this.email = user.email;
        this.password = user.password;
        this.name = user.name;
        this.surname = user.surname;
        this.phone = user.phone;
        this.address = user.address;
        this.isReported=user.isReported;
        this.blocked = user.blocked;
        this.role=user.role;
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
        this.setRole(user.getRole1());
    }

    public Role getRole1() {
        return this.role;
    }
    public String getRole() {
        if(this instanceof Guest){
            return "Guest";
        }
        if(this instanceof Owner){
            return "Owner";
        }
        if(this instanceof Administrator){
            return "Administrator";
        }
        return "User";
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public LocalDateTime getActivationExpiry() {
        return activationExpiry;
    }

    public void setActivationExpiry(LocalDateTime activationExpiry) {
        this.activationExpiry = activationExpiry;
    }
}
