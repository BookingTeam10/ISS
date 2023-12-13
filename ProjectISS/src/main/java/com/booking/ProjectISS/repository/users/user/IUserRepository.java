package com.booking.ProjectISS.repository.users.user;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IUserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email, String password);
    
    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
