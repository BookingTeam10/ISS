package com.booking.ProjectISS.service.users.administrator;

import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.administrator.IAdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class AdministratorService implements IAdministratorService {
    @Autowired
    private IAdministratorRepository administratorRepository;

    @Override
    public AdministratorDTO findOneDTO(Long id) {
        Optional<Administrator> Administrator= administratorRepository.findById(id);
        if(Administrator.isEmpty()){
            return null;
        }
        return new AdministratorDTO(Administrator.get());
    }

    @Override
    public Administrator findOne(Long id) {
        Optional<Administrator> Administrator=administratorRepository.findById(id);
        if(Administrator.isEmpty()){
            return null;
        }
        return Administrator.get();
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
        Administrator found = findOne(id);
        administratorRepository.delete(found);
        administratorRepository.flush();
    }

    @Override
    public AdministratorDTO create(Administrator administrator) throws Exception {
        return new AdministratorDTO(administratorRepository.save(new Administrator(administrator)));
    }

    @Override
    public AdministratorDTO update(Administrator administrator) throws Exception {
        return null;
    }

    @Override
    public AdministratorDTO findAdministrator(LoginDTO login) {
        Administrator administrator=administratorRepository.findByEmail(login.getEmail(),login.getPassword());
        if(administrator==null){
            return null;
        }
        return new AdministratorDTO(administrator.getId(),administrator.getEmail());
    }

    @Override
    public Administrator findUsername(String username) {

        return administratorRepository.findByUsername(username);
    }
}
