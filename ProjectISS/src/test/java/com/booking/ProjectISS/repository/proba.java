package com.booking.ProjectISS.repository;

import com.booking.ProjectISS.model.users.User;
import com.booking.ProjectISS.repository.users.user.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class proba {

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        //User user = new User(null,"1","2","3","4","5","6");
        System.out.println("ABCF");
        User savedUser = userRepository.findOne(1L);
        System.out.println(savedUser);

        String a="Luka123";
        assertThat(savedUser.getName()).isEqualTo(a);
    }
}
