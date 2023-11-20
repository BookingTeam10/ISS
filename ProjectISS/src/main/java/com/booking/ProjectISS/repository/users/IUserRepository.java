package com.booking.ProjectISS.repository.users;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;

import java.util.Collection;

//extends JpaRepository<User, Long>
public interface IUserRepository {
    User findOne(Long id);

    Collection<User> findAll();

    void delete(Long id);

    User create(User user);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
