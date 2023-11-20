package com.booking.ProjectISS.repository.users;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;

import java.util.Collection;
//extends JpaRepository<Owner, Long>
public interface IOwnerRepository {
    Owner findOne(Long id);

    Collection<Owner> findAll();

    void delete(Long id);

    Owner create(Owner owner);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.OwnerDTO(o) from Owner o")
    //public Collection<OwnerDTO> findAllOwners();
}
