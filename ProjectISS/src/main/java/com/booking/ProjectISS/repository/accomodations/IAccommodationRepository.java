package com.booking.ProjectISS.repository.accomodations;

import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface IAccommodationRepository {
    Accommodation findOne(Long id);
    Collection<Accommodation> findAll();
    void delete(Long id);
    Accommodation create(Accommodation accommodation);

    Collection<Accommodation> findAllByOwner(Long id);

    Accommodation createByOwner(Long id, Accommodation accommodation);
}
