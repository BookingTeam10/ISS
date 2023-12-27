package com.booking.ProjectISS.repository.accomodations;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IAccommodationRepository extends JpaRepository<Accommodation,Long> {
    //repository obican obrisati ostaviti samo interfejs
   @Query("select a from Accommodation a where a.owner.id = ?1 ")
   public Collection<Accommodation> findAllByOwner(Long id);

    //@Query () popuniti ovo
    //public Accommodation createByOwner(Long id, Accommodation accommodation);
}
