package com.booking.ProjectISS.service.users.owner;

import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;

import java.util.Collection;
import java.util.Optional;

public interface IOwnerService {

    OwnerDTO findOneDTO(Long id);

    Owner findOne(Long id);

    Collection<OwnerDTO> findAllDTO();

    Collection<Owner> findAll();

    void delete(Long id);

    OwnerDTO create(Owner owner) throws Exception;

    OwnerDTO update(Owner owner) throws Exception;

    boolean reportGuest(Long idGuest, Long idOwner);
}
