package com.booking.ProjectISS.repository.users.administrator;

import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Guest;

import java.util.Collection;

public interface IAdministratorRepository {

    Administrator findOne(Long id);

    Collection<Administrator> findAll();

    void delete(Long id);

    Administrator create(Administrator administrator);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.GuestDTO(g) from Guest g")
    //public Collection<GuestDTO> findAllGuests();
}
