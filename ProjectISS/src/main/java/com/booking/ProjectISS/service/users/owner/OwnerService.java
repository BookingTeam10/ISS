package com.booking.ProjectISS.service.users.owner;

import com.booking.ProjectISS.dto.users.OwnerDTO;
import com.booking.ProjectISS.model.users.Owner;
import com.booking.ProjectISS.repository.users.owner.IOwnerRepository;
import com.booking.ProjectISS.service.users.owner.IOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class OwnerService implements IOwnerService {

    @Autowired
    private IOwnerRepository ownerRepository;

    @Override
    public OwnerDTO findOneDTO(Long id) {
        Owner owner=ownerRepository.findOne(id);
        if(owner==null){
            return null;
        }
        return new OwnerDTO(owner);
    }

    @Override
    public Owner findOne(Long id) {
        Owner owner=ownerRepository.findOne(id);
        if(owner==null){
            return null;
        }
        return owner;
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
        ownerRepository.delete(id);
    }

    @Override
    public OwnerDTO create(Owner owner) throws Exception {
        if (owner.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Owner savedOwner = ownerRepository.create(owner);
        return new OwnerDTO(savedOwner);
    }

    @Override
    public OwnerDTO update(Owner owner) throws Exception {
        return null;
    }
    @Override
    public boolean reportGuest(Long idGuest, Long idOwner) {
        return true;
    }

}
