package com.booking.ProjectISS.service.users;

import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.IAdministratorRepository;
import com.booking.ProjectISS.repository.users.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class AdministratorService implements IAdministratorService{
    @Autowired
    private IAdministratorRepository administratorRepository;

    @Override
    public AdministratorDTO findOneDTO(Long id) {
        Administrator administrator=administratorRepository.findOne(id);
        if(administrator==null){
            return null;
        }
        return new AdministratorDTO(administrator);
    }

    @Override
    public Administrator findOne(Long id) {
        Administrator administrator=administratorRepository.findOne(id);
        if(administrator==null){
            return null;
        }
        return administrator;
    }

    @Override
    public Collection<AdministratorDTO> findAllDTO() {
        Collection<Administrator> administrators = administratorRepository.findAll();
        Collection<AdministratorDTO> ret = new ArrayList<AdministratorDTO>();       //primitive way, delete other this line after
        for(Administrator g : administrators) {
            ret.add(new AdministratorDTO(g));
        }
        return ret;
    }

    @Override
    public Collection<Administrator> findAll() {
        Collection<Administrator> administrators = administratorRepository.findAll();
        return administrators;
    }

    @Override
    public void delete(Long id) {
        administratorRepository.delete(id);
    }

    @Override
    public AdministratorDTO create(Administrator administrator) throws Exception {
        if (administrator.getId() != null) {
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Administrator savedAdministrator = administratorRepository.create(administrator);
        return new AdministratorDTO(savedAdministrator);
    }

    @Override
    public AdministratorDTO update(Administrator administrator) throws Exception {
        return null;
    }
}
