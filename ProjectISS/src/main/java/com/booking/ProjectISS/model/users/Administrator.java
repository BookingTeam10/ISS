package com.booking.ProjectISS.model.users;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="administrators")
public class Administrator extends User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name="all_users")
    private List<User> users;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name="accommodations")
    private List<Accommodation> accomodations;
    @Id
    private Long id;

    public Administrator(Long id, String email, String password, String name, String surname, String phone, String address, List<User> users, List<Accommodation> accomodations) {
        super(id, email, password, name, surname, phone, address);
        this.users = users;
        this.accomodations = accomodations;
    }

    public Administrator(Long id, String email, String password, String name, String surname, String phone, String address) {
        super(id, email, password, name, surname, phone, address);
        this.users=new ArrayList<>();
        this.accomodations=new ArrayList<>();
    }

    public Administrator(Administrator a){
        super(a.getId(),a.getEmail(),a.getPassword(),a.getName(),a.getSurname(),a.getPhone(),a.getAddress(),a.isReported(),a.isBlocked());
        this.users=a.users;
        this.accomodations=a.accomodations;
    }

    public Administrator() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Accommodation> getAccomodations() {
        return accomodations;
    }

    public void setAccomodations(List<Accommodation> accomodations) {
        this.accomodations = accomodations;
    }
    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + super.getId() +
                ", email='" + super.getEmail() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                ", users=" + users +
                ", accomodations=" + accomodations +
                '}';
    }

    public void copyValues(Administrator administrator) {
        this.setEmail(administrator.getEmail());
        this.setPassword(administrator.getPassword());
        this.setUsers(administrator.getUsers());
        this.setAccomodations(administrator.getAccomodations());
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
