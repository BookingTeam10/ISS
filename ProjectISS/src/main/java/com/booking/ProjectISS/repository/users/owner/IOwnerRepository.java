package com.booking.ProjectISS.repository.users.owner;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
public interface IOwnerRepository extends JpaRepository<Owner,Long> {

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.OwnerDTO(o) from Owner o")
    //public Collection<OwnerDTO> findAllOwners();
}
