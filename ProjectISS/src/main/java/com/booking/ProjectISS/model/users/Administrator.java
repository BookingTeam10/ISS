package com.booking.ProjectISS.model.users;

import com.booking.ProjectISS.model.accomodations.Accommodation;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Administrator {

    //@javax.persistence.Id
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)         //increment add id+1, e.g. if previous id was 5, the next will be 6
    private Long id;
    private String email;
    private String password;
    private List<User> users;
    private List<Accommodation> accomodations;
    public Administrator(Long id, String email, String password, List<User> users, List<Accommodation> accomodations) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.users = users;
        this.accomodations = accomodations;
    }

    public Administrator(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Administrator(){
        users=new ArrayList<>();
        accomodations=new ArrayList<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
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
}
