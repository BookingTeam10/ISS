package com.booking.ProjectISS.repository.users.user;

import com.booking.ProjectISS.model.users.Guest;
import com.booking.ProjectISS.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email, String password);

    Optional<User> findByEmail(String email);
    @Query("select u from User u where u.activationCode = ?1")
    User findByActivationCode(String activationCode);

    @Query("select count(*) from User u where u.email = ?1")
    Long doesUsernameExist(String username);
}
