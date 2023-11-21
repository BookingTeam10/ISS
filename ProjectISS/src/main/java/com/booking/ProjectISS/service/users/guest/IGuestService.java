package com.booking.ProjectISS.service.users.guest;

import com.booking.ProjectISS.dto.users.GuestDTO;
import com.booking.ProjectISS.model.users.Guest;

import java.util.Collection;

public interface IGuestService {

    GuestDTO findOneDTO(Long id);

    Guest findOne(Long id);

    Collection<GuestDTO> findAllDTO();

    Collection<Guest> findAll();

    void delete(Long id);

    GuestDTO create(Guest guest) throws Exception;

    GuestDTO update(Guest guest) throws Exception;
}
