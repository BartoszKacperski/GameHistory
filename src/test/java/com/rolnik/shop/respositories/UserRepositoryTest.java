package com.rolnik.shop.respositories;

import com.rolnik.shop.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    void whenFindByUsername_ThanReturnUser() {
        //given
        User user = new User(
                "login",
                "email",
                "password",
                Collections.emptySet()
        );

        entityManager.persist(user);
        entityManager.flush();

        //when
        User foundUser = userRepository.findByUsername("login").orElse(null);

        //then
        assertNotNull(foundUser);
        assertEquals(foundUser.getUsername(), user.getUsername());

    }
}