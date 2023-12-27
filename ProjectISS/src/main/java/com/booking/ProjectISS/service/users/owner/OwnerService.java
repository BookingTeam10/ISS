package com.booking.ProjectISS.service.users.owner;

import com.booking.ProjectISS.dto.accomodations.AccommodationDTO;
import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.accomodations.Accommodation;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class OwnerService implements IOwnerService {

    @Autowired
    private IOwnerRepository ownerRepository;

    @Override
    public OwnerDTO findOneDTO(Long id) {
        Optional<Owner> Owner=ownerRepository.findById(id);
        if(Owner.isEmpty()){
            return null;
        }
        return new OwnerDTO(Owner.get());
    }

    @Override
    public Owner findOne(Long id) {
        Optional<Owner> found = ownerRepository.findById(id);

        if(found.isEmpty()){
            return null;
        }
        return found.get();
    }

    @Override
    public Collection<OwnerDTO> findAllDTO() {
        Collection<Owner> owners = ownerRepository.findAll();
        Collection<OwnerDTO> ret = new ArrayList<OwnerDTO>();       //primitive way, delete other this line after
        for(Owner g : owners) {
            ret.add(new OwnerDTO(g));
        }
        return ret;
    }

    @Override
    public Collection<Owner> findAll() {
        Collection<Owner> owners = ownerRepository.findAll();
        return owners;
    }

    @Override
    public void delete(Long id) {
        Owner found = findOne(id);
        ownerRepository.delete(found);
        ownerRepository.flush();
    }

    @Override
    public OwnerDTO create(Owner owner) throws Exception {
        return new OwnerDTO(ownerRepository.save(new Owner(owner)));
    }

    @Override
    public OwnerDTO update(Owner ownerForUpdate) throws Exception {

        Optional<Owner> optionalOwner = this.ownerRepository.findById(ownerForUpdate.id());
        optionalOwner.ifPresent(oldOwner->{
            oldOwner.setEmail(ownerForUpdate.getEmail());
            oldOwner.setPassword(ownerForUpdate.getPassword());
            oldOwner.setName(ownerForUpdate.getName());
            oldOwner.setSurname(ownerForUpdate.getSurname());
            oldOwner.setPhone(ownerForUpdate.getPhone());
            oldOwner.setAddress(ownerForUpdate.getAddress());
            oldOwner.setBlocked(ownerForUpdate.isBlocked());
            oldOwner.setCreatedNotification(ownerForUpdate.isCreatedNotification());
            oldOwner.setCancelledNotification(ownerForUpdate.isCancelledNotification());
            oldOwner.setRateAccomodationNotification(ownerForUpdate.isRateAccommodationNotification());
            oldOwner.setRateMeNotification(ownerForUpdate.isRateMeNotification());

            this.ownerRepository.save(oldOwner);
        });

        return new OwnerDTO(ownerForUpdate);
    }
    @Override
    public boolean reportGuest(Long idGuest, Long idOwner) {
        return true;
    }

    @Override
    public Owner findUsername(String username) {
        return this.ownerRepository.findByEmail(username);
    }

}
