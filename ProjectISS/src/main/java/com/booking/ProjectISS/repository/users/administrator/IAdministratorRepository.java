package com.booking.ProjectISS.repository.users.administrator;

import com.booking.ProjectISS.model.users.Administrator;
import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface IAdministratorRepository extends JpaRepository<Administrator,Long> {
    @Query("select a from Administrator a where a.email = ?1 and a.password=?2 ")
    Administrator findByEmail(String email, String password);
}
