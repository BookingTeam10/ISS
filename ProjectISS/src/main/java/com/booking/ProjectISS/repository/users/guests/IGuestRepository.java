package com.booking.ProjectISS.repository.users.guests;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

//extends JpaRepository<Guest, Long>
public interface IGuestRepository extends JpaRepository<Guest,Long>  {

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
