package com.booking.ProjectISS.repository;

import com.booking.ProjectISS.model.users.Guest;

import java.util.Collection;

//extends JpaRepository<Guest, Long>
public interface IGuestRepository{
    Guest findOne(Long id);

    Collection<Guest> findAll();

    void delete(Long id);

    Guest create(Guest guest);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
