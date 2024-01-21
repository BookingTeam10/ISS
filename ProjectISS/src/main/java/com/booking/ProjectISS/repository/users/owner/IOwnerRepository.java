package com.booking.ProjectISS.repository.users.owner;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
public interface IOwnerRepository extends JpaRepository<Owner,Long> {

    @Query("select o from Owner o where o.email = ?1")
    Owner findByEmail(String username);

    //for later, add @OneToOne
    //@Query("select new com.booking.ProjectISS.dto.OwnerDTO(o) from Owner o")
    //public Collection<OwnerDTO> findAllOwners();
}
