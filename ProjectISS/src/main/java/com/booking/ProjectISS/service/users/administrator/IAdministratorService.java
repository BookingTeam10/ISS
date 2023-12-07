package com.booking.ProjectISS.service.users.administrator;

import com.booking.ProjectISS.dto.users.AdministratorDTO;
import com.booking.ProjectISS.dto.users.LoginDTO;
import com.booking.ProjectISS.dto.users.UserDTO;
import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.User;

import java.util.Collection;

public interface IAdministratorService {

    AdministratorDTO findOneDTO(Long id);

    Administrator findOne(Long id);

    Collection<AdministratorDTO> findAllDTO();

    Collection<Administrator> findAll();

    void delete(Long id);

    AdministratorDTO create(Administrator administrator) throws Exception;

    AdministratorDTO update(Administrator administrator) throws Exception;

    AdministratorDTO findAdministrator(LoginDTO login);
}
