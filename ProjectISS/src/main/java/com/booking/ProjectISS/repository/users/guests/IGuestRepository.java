package com.booking.ProjectISS.repository.users.guests;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

//extends JpaRepository<Guest, Long>
public interface IGuestRepository extends JpaRepository<Guest,Long>  {
    @Query("select g from Guest g where g.email = ?1")
    Guest findByEmail(String username);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
